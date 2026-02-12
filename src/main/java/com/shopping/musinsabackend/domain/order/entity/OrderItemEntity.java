package com.shopping.musinsabackend.domain.order.entity;

import com.shopping.musinsabackend.domain.product.entity.ProductEntity;
import com.shopping.musinsabackend.global.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "order_item")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class OrderItemEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderItemId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private OrderEntity order; // 주문서와 연결

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private ProductEntity product; // 상품과 연결

    @Column(nullable = false)
    private Integer orderPrice; // 주문 당시 가격

    @Column(nullable = false)
    private Integer orderCount; // 주문 수량

    public void setOrder(OrderEntity order) {
        this.order = order;
    }
}
