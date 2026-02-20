package com.shopping.musinsabackend.domain.order.dto.response;

import com.shopping.musinsabackend.domain.order.entity.OrderEntity;
import com.shopping.musinsabackend.domain.order.entity.OrderStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(title = "주문내역 전체 응답 DTO")
public class OrderPastResponse {

    @Schema(description = "주문 고유번호", example = "1")
    private Long orderId;

    @Schema(description = "주문 상태", example = "ORDER")
    private OrderStatus orderStatus;

    @Schema(description = "주문 시간", example = "2026-02-12T16:00:00")
    private LocalDateTime orderAt;

    @Schema(description = "총 주문 가격", example = "50000")
    private Integer totalPrice;

    @Schema(description = "대표 상품명", example = "나이키 후드티 외 2건")
    private String representativeProductName;

    public static OrderPastResponse from(OrderEntity order) {
        String prodName = order.getOrderItems().get(0).getProduct().getProductName();
        int size = order.getOrderItems().size();

        if (size > 1) {
            prodName += " 외 " + (size - 1) + "건";
        }

        return OrderPastResponse.builder()
                .orderId(order.getOrderId())
                .orderStatus(order.getOrderStatus())
                .orderAt(order.getOrderAt())
                .totalPrice(order.getTotalPrice())
                .representativeProductName(prodName)
                .build();
    }
}
