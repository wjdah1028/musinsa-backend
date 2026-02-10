package com.shopping.musinsabackend.domain.product.mapper;

import com.shopping.musinsabackend.domain.product.dto.response.CategoryReadResponse;
import com.shopping.musinsabackend.domain.product.entity.CategoryEntity;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class CategoryReadMapper {

    // Entity -> Response DTO
    public CategoryReadResponse toResponse(CategoryEntity entity) {
        return CategoryReadResponse.builder()
                .categoryId(entity.getCategoryId())
                .categoryName(entity.getCategoryName())
                .depth(entity.getDepth())
                .children(entity.getChildren().stream().map(this::toResponse).collect(Collectors.toList()))
                .build();
    }
}
