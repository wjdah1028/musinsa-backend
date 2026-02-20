package com.shopping.musinsabackend.domain.product.entity;

import com.shopping.musinsabackend.domain.user.entity.Gender;
import com.shopping.musinsabackend.global.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "product")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class ProductEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId; // 상품 고유번호

    @Column(length = 100, nullable = false)
    private String productName; // 상품 이름

    @Column(length = 500, nullable = true)
    private String productContent; // 상품 설명

    @Column(nullable = false)
    private int price; // 상품 가격

    @Column(nullable = false)
    @Builder.Default // 0으로 시작
    private int reviewCount = 0; // 리뷰 개수

    @Column(nullable = false)
    @Builder.Default
    private int productLike = 0; // 상품 좋아요 개수

    @Enumerated(EnumType.STRING)
    @Column(length = 10, nullable = false)
    private Gender gender; // 상품의 추천 성별

    @Column(nullable = false)
    private int stock; // 상품 재고

    @Builder.Default
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductImageEntity> images = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id", nullable = false)
    private BrandEntity brand;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private CategoryEntity category;

    public void update(String productName, String productContent, Integer price, Integer stock, Gender gender, BrandEntity brand, CategoryEntity category) {
        if (productName != null && !productName.isBlank()) {
            this.productName = productName;
        }
        if (productContent != null) {
            this.productContent = productContent;
        }
        if (price != null) {
            this.price = price;
        }
        if (stock != null) {
            this.stock = stock;
        }
        if (gender != null) {
            this.gender = gender;
        }
        if (brand != null) {
            this.brand = brand;
        }
        if (category != null) {
            this.category = category;
        }
    }

    public void removeStock(int count) {
        int restStock = this.stock - count;
        if (restStock < 0) {
            throw new IllegalStateException("재고가 부족합니다.");
        }
        this.stock = restStock;
    }

    public void addStock(int count) {
        this.stock += count;
    }
}
