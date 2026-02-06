package com.shopping.musinsabackend.domain.product.entity;

import com.shopping.musinsabackend.global.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "brand")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class BrandEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long brandId; // 브랜드별 고유 번호

    @Column(length = 100, nullable = false)
    private String brandName;
}
