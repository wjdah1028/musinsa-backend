package com.shopping.musinsabackend.domain.product.entity;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "product_image")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ProductImageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 500, nullable = false)
    private String imageUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private ProductEntity product;
}
