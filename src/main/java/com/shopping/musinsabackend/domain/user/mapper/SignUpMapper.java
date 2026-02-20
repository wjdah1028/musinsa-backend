package com.shopping.musinsabackend.domain.user.mapper;

import com.shopping.musinsabackend.domain.user.dto.request.SignUpRequest;
import com.shopping.musinsabackend.domain.user.dto.response.SignUpResponse;
import com.shopping.musinsabackend.domain.user.entity.Role;
import com.shopping.musinsabackend.domain.user.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class SignUpMapper {

    // 회원가입시
    // Request DTO > Entity로 변환
    public UserEntity toEntity(SignUpRequest request, String encodedPassword) {
        return UserEntity.builder()
                .email(request.getEmail())
                .password(encodedPassword)
                .name(request.getName())
                .age(request.getAge())
                .gender(request.getGender())
                .phone(request.getPhone())
                .zipcode(request.getZipcode())
                .address(request.getAddress())
                .addressDetail(request.getAddressDetail())
                .role(Role.USER)
                .build();
    }

    // 회원가입시
    // Entity -> Response DTO로 변환
    public SignUpResponse toResponse(UserEntity user) {
        return SignUpResponse.builder()
                .userId(user.getUserId())
                .email(user.getEmail())
                .name(user.getName())
                .age(user.getAge())
                .gender(user.getGender())
                .phone(user.getPhone())
                .zipcode(user.getZipcode())
                .address(user.getAddress())
                .addressDetail(user.getAddressDetail())
                .role(user.getRole())
                .build();
    }
}