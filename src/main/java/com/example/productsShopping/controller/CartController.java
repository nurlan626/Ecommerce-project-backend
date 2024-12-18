
// Cart Controller
package com.example.productsShopping.controller;

import com.example.productsShopping.dto.CartItemDto;
import com.example.productsShopping.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    @GetMapping
    public ResponseEntity<List<CartItemDto>> getUserCart(Principal principal) {
        if (principal == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        List<CartItemDto> cart = cartService.getUserCart(principal.getName());
        return ResponseEntity.ok(cart);
    }

    @PostMapping("/{productId}")
    public ResponseEntity<CartItemDto> addToCart(
            Principal principal,
            @PathVariable Long productId,
            @RequestParam(defaultValue = "1") Integer quantity
    ) {
        // Проверка, что пользователь авторизован
        if (principal == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        // Логирование для отладки
        System.out.println("Добавление в корзину для пользователя: " + principal.getName());

        // Добавление товара в корзину
        CartItemDto cartItem = cartService.addToCart(principal.getName(), productId, quantity);

        if (cartItem != null) {
            return ResponseEntity.ok(cartItem);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null); // Ошибка добавления
        }
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<?> removeFromCart(
            Principal principal,
            @PathVariable Long productId
    ) {
        if (principal == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        cartService.removeFromCart(principal.getName(), productId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{productId}")
    public ResponseEntity<?> updateCartItemQuantity(
            Principal principal,
            @PathVariable Long productId,
            @RequestParam Integer quantity
    ) {
        if (principal == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        CartItemDto updatedItem = cartService.updateCartItemQuantity(principal.getName(), productId, quantity);

        return updatedItem != null
                ? ResponseEntity.ok(updatedItem)
                : ResponseEntity.noContent().build();
    }


}