package com.shopping.musinsabackend.domain.product.repository;

import com.shopping.musinsabackend.domain.product.entity.BrandEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BrandRepository extends JpaRepository<BrandEntity, Long> {
    boolean existsByBrandName(String brandName);
}
