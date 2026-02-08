package com.shopping.musinsabackend.domain.product.repository;

import com.shopping.musinsabackend.domain.product.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

}
