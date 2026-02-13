package com.shopping.musinsabackend.domain.order.entity;

import com.shopping.musinsabackend.domain.order.exception.OrderErrorCode;
import com.shopping.musinsabackend.domain.user.entity.UserEntity;
import com.shopping.musinsabackend.global.common.BaseTimeEntity;
import com.shopping.musinsabackend.global.exception.CustomException;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class OrderEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId; // 주문 고유번호
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user; // 주문자

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    @lombok.Builder.Default
    private List<OrderItemEntity> orderItems = new ArrayList<>();

    @Column(nullable = false)
    private Integer totalPrice;

    @Column(length = 10, nullable = false)
    private String zipcode; // 우편번호

    @Column(length = 200, nullable = false)
    private String address; // 기본 주소

    @Column(length = 100, nullable = false)
    private String addressDetail; // 상세 주소

    @Column(nullable = false)
    private LocalDateTime orderAt; // 주문 날짜 + 시간

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus orderStatus; // 주문 상태

    // 양방향 관계 설정을 위한 메서드
    public void addOrderItem(OrderItemEntity orderItem) {
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    public void updateTotalPrice(Integer totalPrice) {
        this.totalPrice = totalPrice;
    }

    // 배송 취소하면 재고 다시 추가
    public void cancel() {

        // 취소된 주문인지 확인
        if (this.orderStatus == OrderStatus.CANCEL) {
            throw new CustomException(OrderErrorCode.ALREADY_CANCEL);
        }

        // 주문 상태 변경
        this.orderStatus = OrderStatus.CANCEL;

        // 재고 추가
        for (OrderItemEntity orderItem : orderItems) {
            orderItem.cancel();
        }
    }
}
