package com.shopping.musinsabackend.domain.product.mapper;

import com.shopping.musinsabackend.domain.product.dto.response.ProductReadResponse;
import com.shopping.musinsabackend.domain.product.entity.CategoryEntity;
import com.shopping.musinsabackend.domain.product.entity.ProductEntity;
import org.springframework.stereotype.Component;

@Component
public class ProductReadMapper {

    // Entity -> Response DTO 변환
    public ProductReadResponse toResponse(ProductEntity product) {
        return ProductReadResponse.builder()
                .productId(product.getProductId())
                .productName(product.getProductName())
                .price(product.getPrice())
                .brandName(product.getBrand().getBrandName())
                .categoryName(getCategoryPath(product.getCategory()))
                .build();
    }

    // 카테고리 경로
    private String getCategoryPath(CategoryEntity category) {
        
        // 현재 카테고리 출력
        String path = category.getCategoryName();
        CategoryEntity parent = category.getParent();
        
        // 부모가 null일때까지 위로 올라가서 출력
        while (parent != null) {
            path = parent.getCategoryName() + " > " + path;
            parent = parent.getParent();
        }

        // 경로 반환
        return path;
    }
}
