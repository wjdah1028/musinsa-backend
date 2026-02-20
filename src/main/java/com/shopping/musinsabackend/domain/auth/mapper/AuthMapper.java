package com.shopping.musinsabackend.domain.auth.mapper;

import com.shopping.musinsabackend.domain.auth.dto.response.LoginResponse;
import com.shopping.musinsabackend.domain.user.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class AuthMapper {
    public LoginResponse toLoginResponse(UserEntity user, String accessToken, Long expireTime) {
        return LoginResponse.builder()
                .accessToken(accessToken)
                .userId(user.getUserId())
                .email(user.getEmail())
                .name(user.getName())
                .role(user.getRole())
                .expiresAt(System.currentTimeMillis() + expireTime)
                .build();
    }
}
