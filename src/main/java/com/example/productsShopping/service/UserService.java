package com.example.productsShopping.service;


import com.example.productsShopping.dto.UserProfileDTO;
import com.example.productsShopping.entity.User;
import com.example.productsShopping.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

//    public Object getUserProfile(String username) {
//        User user = userRepository.findByUsername(username)
//                .orElseThrow(() -> new RuntimeException("User not found"));
//
//        return new Object() {
//            public String getUsername() {
//                return user.getUsername();
//            }
//
//            public String getEmail() {
//                return user.getEmail();
//            }
//        };
//    }
    public UserProfileDTO getUserProfile(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return new UserProfileDTO(user.getUsername(), user.getEmail());
    }
}

