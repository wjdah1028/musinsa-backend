package com.shopping.musinsabackend.domain.product.repository;

import com.shopping.musinsabackend.domain.product.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {
    boolean existsByCategoryName(String categoryName);

    // 남성 , 여성 카테고리 조회
    List<CategoryEntity> findAllByParentIsNull();
}
