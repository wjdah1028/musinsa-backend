package com.shopping.musinsabackend.domain.product.repository;

import com.shopping.musinsabackend.domain.product.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {
    boolean existsByCategoryName(String categoryName);
}
