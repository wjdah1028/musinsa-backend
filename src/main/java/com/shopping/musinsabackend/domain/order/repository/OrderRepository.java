package com.shopping.musinsabackend.domain.order.repository;

import com.shopping.musinsabackend.domain.order.entity.OrderEntity;
import com.shopping.musinsabackend.domain.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {

    // 사용자별로 주문 목록 조회
    List<OrderEntity> findAllByUserOrderByOrderAtDesc(UserEntity user);
}
