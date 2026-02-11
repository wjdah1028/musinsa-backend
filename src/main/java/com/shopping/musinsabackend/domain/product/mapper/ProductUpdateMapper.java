package com.shopping.musinsabackend.domain.product.mapper;

import com.shopping.musinsabackend.domain.product.dto.response.ProductUpdateResponse;
import com.shopping.musinsabackend.domain.product.entity.ProductEntity;
import com.shopping.musinsabackend.domain.product.entity.ProductImageEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Component
public class ProductUpdateMapper {

    public ProductUpdateResponse toUpdateResponse(ProductEntity product) {
        return ProductUpdateResponse.builder()
                .productId(product.getProductId())
                .productName(product.getProductName())
                .productContent(product.getProductContent())
                .price(product.getPrice())
                .stock(product.getStock())
                .gender(product.getGender())
                .brandName(product.getBrand() != null ? product.getBrand().getBrandName() : null)
                .categoryName(product.getCategory() != null ? product.getCategory().getCategoryName() : null)
                .imageUrls(product.getImages().stream().map(ProductImageEntity::getImageUrl).collect(Collectors.toList()))
                .reviewCount(product.getReviewCount())
                .productLike(product.getProductLike())
                .updatedAt(LocalDateTime.now())
                .build();
    }
}
