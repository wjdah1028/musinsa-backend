package com.shopping.musinsabackend.domain.product.entity;

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
@Table(name = "category")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class CategoryEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryId; // 카테고리별 고유번호

    @Column(length = 100, nullable = false)
    private String categoryName; // 카테고리 이름


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private CategoryEntity parent; // 부모 카테고리

    @Builder.Default
    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
    private List<CategoryEntity> children = new ArrayList<>(); // 자식 카테고리
    
    private Integer depth; // 깊이
}
