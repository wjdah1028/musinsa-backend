package com.shopping.musinsabackend.domain.cart.repository;

import com.shopping.musinsabackend.domain.cart.entity.CartEntity;
import com.shopping.musinsabackend.domain.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<CartEntity, Long> {

    // 사용자의 장바구니를 찾는 메서드
    Optional<CartEntity> findByUser(UserEntity user);
}
