package com.shopping.musinsabackend.domain.user.mapper;

import com.shopping.musinsabackend.domain.user.dto.response.InfoResponse;
import com.shopping.musinsabackend.domain.user.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class InfoMapper {

    // Entity -> Response DTO 변환
    public InfoResponse toResponse(UserEntity user) {
        return InfoResponse.builder()
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
