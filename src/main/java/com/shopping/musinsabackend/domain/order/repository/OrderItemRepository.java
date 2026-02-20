package com.shopping.musinsabackend.domain.order.repository;

import com.shopping.musinsabackend.domain.order.entity.OrderItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItemEntity, Long> {

}
