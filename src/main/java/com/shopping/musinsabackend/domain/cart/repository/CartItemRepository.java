package com.shopping.musinsabackend.domain.cart.repository;

import com.shopping.musinsabackend.domain.cart.entity.CartEntity;
import com.shopping.musinsabackend.domain.cart.entity.CartItemEntity;
import com.shopping.musinsabackend.domain.product.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItemEntity, Long> {

    // 특정 장바구니에 특정 상품이 담겨 있는지 확인
    Optional<CartItemEntity> findByCartAndProduct(CartEntity cart, ProductEntity product);

    // 장바구니 전체 확인
    List<CartItemEntity> findByCart(CartEntity cart);
}
