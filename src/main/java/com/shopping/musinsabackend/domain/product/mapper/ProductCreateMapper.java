package com.shopping.musinsabackend.domain.product.mapper;

import com.shopping.musinsabackend.domain.product.dto.request.ProductCreateRequest;
import com.shopping.musinsabackend.domain.product.dto.response.ProductCreateResponse;
import com.shopping.musinsabackend.domain.product.entity.BrandEntity;
import com.shopping.musinsabackend.domain.product.entity.CategoryEntity;
import com.shopping.musinsabackend.domain.product.entity.ProductEntity;
import org.springframework.stereotype.Component;

@Component
public class ProductCreateMapper {

    // Request DTO -> Entity 변환
    public ProductEntity toEntity(ProductCreateRequest request, BrandEntity brand, CategoryEntity category, String imageUrl) {
        return ProductEntity.builder()
                .productName(request.getProductName())
                .productContent(request.getProductContent())
                .price(request.getPrice())
                .stock(request.getStock())
                .gender(request.getGender())
                .image(imageUrl)
                .brand(brand)
                .category(category)
                .build();
    }

    // Entity -> Response DTO 변환
    public ProductCreateResponse toResponse(ProductEntity product) {
        return ProductCreateResponse.builder()
                .productId(product.getProductId())
                .productName(product.getProductName())
                .productContent(product.getProductContent())
                .price(product.getPrice())
                .stock(product.getStock())
                .gender(product.getGender())
                .imageUrl(product.getImage())
                .reviewCount(product.getReviewCount())
                .productLike(product.getProductLike())
                .brandName(product.getBrand().getBrandName())
                .categoryName(product.getCategory().getCategoryName())
                .createdAt(product.getCreatedAt())
                .build();
    }
}
