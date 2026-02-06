package com.shopping.musinsabackend.domain.product.entity;

import com.shopping.musinsabackend.domain.user.entity.Gender;
import com.shopping.musinsabackend.global.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

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

    @Column(nullable = false, length = 500)
    private String image;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brandId", nullable = false)
    private BrandEntity brand;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoryId", nullable = false)
    private CategoryEntity category;
}
