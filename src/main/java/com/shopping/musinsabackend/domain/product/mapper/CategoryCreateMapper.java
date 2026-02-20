package com.shopping.musinsabackend.domain.product.mapper;

import com.shopping.musinsabackend.domain.product.dto.request.CategoryCreateRequest;
import com.shopping.musinsabackend.domain.product.dto.response.CategoryCreateResponse;
import com.shopping.musinsabackend.domain.product.entity.CategoryEntity;
import org.springframework.stereotype.Component;

@Component
public class CategoryCreateMapper {

    // Request DTO -> Entity 변환
    public CategoryEntity toEntity(CategoryCreateRequest request, CategoryEntity parent) {
        return CategoryEntity.builder()
                .categoryName(request.getCategoryName())
                .parent(parent)
                .build();
    }

    // Entity -> Response DTO 변환
    public CategoryCreateResponse toResponse(CategoryEntity category) {
        return CategoryCreateResponse.builder()
                .categoryId(category.getCategoryId())
                .categoryName(category.getCategoryName())
                .build();
    }
}
