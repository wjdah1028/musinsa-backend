package com.shopping.musinsabackend.domain.product.mapper;

import com.shopping.musinsabackend.domain.product.dto.request.BrandCreateRequest;
import com.shopping.musinsabackend.domain.product.dto.response.BrandCreateResponse;
import com.shopping.musinsabackend.domain.product.entity.BrandEntity;
import org.springframework.stereotype.Component;

@Component
public class BrandCreateMapper {

    // Request DTO -> Entity 변환
    public BrandEntity toEntity(BrandCreateRequest request) {
        return BrandEntity.builder()
                .brandName(request.getBrandName())
                .build();
    }

    // Entity -> Response DTO 변환
    public BrandCreateResponse toResponse(BrandEntity brand) {
        return BrandCreateResponse.builder()
                .brandId(brand.getBrandId())
                .brandName(brand.getBrandName())
                .build();
    }
}
