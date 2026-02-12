package com.shopping.musinsabackend.domain.order.mapper;

import com.shopping.musinsabackend.domain.order.dto.response.OrderCreateResponse;
import com.shopping.musinsabackend.domain.order.dto.response.OrderItemResponse;
import com.shopping.musinsabackend.domain.order.entity.OrderEntity;
import com.shopping.musinsabackend.domain.order.entity.OrderItemEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderCreateMapper {

    // 주문 전체 Entity -> Resposnse DTO
    public OrderCreateResponse toResponse(OrderEntity order) {

        // 주문 안에 있는 상품 리스트를 하나씩 DTO로 변환
        List<OrderItemResponse> orderItemResponses = order.getOrderItems().stream().map(this::toOrderItemResponse).collect(Collectors.toList());
        
        return OrderCreateResponse.builder()
                .orderId(order.getOrderId())
                .orderStatus(order.getOrderStatus())
                .orderAt(order.getOrderAt())
                .totalPrice(order.getTotalPrice())
                .zipcode(order.getZipcode())
                .address(order.getAddress())
                .addressDetail(order.getAddressDetail())
                .orderItems(orderItemResponses)
                .build();
    }
    
    // 주문 상품 개별 전환 (Entity -> DTO)
    public OrderItemResponse toOrderItemResponse(OrderItemEntity orderItem) {
        
        // 이미지가 있으면 첫 번째것만 가져오고 아니면 null or 기본 이미지
        String thumbnailUrl = null;
        if (orderItem.getProduct().getImages() != null && !orderItem.getProduct().getImages().isEmpty()) {
            thumbnailUrl = orderItem.getProduct().getImages().get(0).getImageUrl();
        }
        
        return OrderItemResponse.builder()
                .productId(orderItem.getProduct().getProductId())
                .productName(orderItem.getProduct().getProductName())
                .brandName(orderItem.getProduct().getBrand().getBrandName())
                .orderPrice(orderItem.getOrderPrice())
                .orderCount(orderItem.getOrderCount())
                .totalPrice(orderItem.getOrderPrice() * orderItem.getOrderCount())
                .imageUrl(thumbnailUrl)
                .build();
    }
}
