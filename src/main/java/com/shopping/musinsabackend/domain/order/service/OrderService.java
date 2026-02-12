package com.shopping.musinsabackend.domain.order.service;

import com.shopping.musinsabackend.domain.order.dto.request.OrderCreateRequest;
import com.shopping.musinsabackend.domain.order.dto.request.OrderProductDto;
import com.shopping.musinsabackend.domain.order.dto.response.OrderDetailResponse;
import com.shopping.musinsabackend.domain.order.entity.OrderEntity;
import com.shopping.musinsabackend.domain.order.entity.OrderItemEntity;
import com.shopping.musinsabackend.domain.order.entity.OrderStatus;
import com.shopping.musinsabackend.domain.order.exception.OrderErrorCode;
import com.shopping.musinsabackend.domain.order.mapper.OrderCreateMapper;
import com.shopping.musinsabackend.domain.order.repository.OrderRepository;
import com.shopping.musinsabackend.domain.product.entity.ProductEntity;
import com.shopping.musinsabackend.domain.product.exception.ProductErrorCode;
import com.shopping.musinsabackend.domain.product.repository.ProductRepository;
import com.shopping.musinsabackend.domain.user.entity.UserEntity;
import com.shopping.musinsabackend.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final OrderCreateMapper orderCreateMapper;

    // 주문 생성 로직
    @Transactional
    public OrderDetailResponse createOrder(UserEntity user, OrderCreateRequest request) {

        // 주문할 상품 리스트 생성
        List<OrderItemEntity> orderItems = new ArrayList<>();
        int totalOrderPrice = 0;

        for (OrderProductDto productDto : request.getOrderProducts()) {

            // 상품 조회
            ProductEntity product = productRepository.findById(productDto.getProductId())
                    .orElseThrow(() -> new CustomException(ProductErrorCode.PRODUCT_NOT_FOUND));

            // 재고 확인 및 차감
            if (product.getStock() < productDto.getItemCount()) {
                throw new CustomException(OrderErrorCode.STOCK_EMPTY);
            }
            product.removeStock(productDto.getItemCount());

            // 주문 상품 엔티티 생성
            OrderItemEntity orderItem = OrderItemEntity.builder()
                    .product(product)
                    .orderPrice(product.getPrice())
                    .orderCount(productDto.getItemCount())
                    .build();

            orderItems.add(orderItem);
            totalOrderPrice += (product.getPrice() * productDto.getItemCount());
        }

        // 주문 엔티티 생성
        OrderEntity order = OrderEntity.builder()
                .user(user)
                .zipcode(request.getZipcode())
                .address(request.getAddress())
                .addressDetail(request.getAddressDetail())
                .orderStatus(OrderStatus.ORDER)
                .orderAt(LocalDateTime.now())
                .totalPrice(totalOrderPrice)
                .orderItems(new ArrayList<>())
                .build();

        // 주문서와 주문 상품 연결
        for (OrderItemEntity orderItem : orderItems) {
            order.addOrderItem(orderItem);
        }

        // 저장
        orderRepository.save(order);

        log.info("주문 성공: orderId={}, user={}, totalPrice={}", order.getOrderId(), user.getEmail(), totalOrderPrice);

        // 반환
        return orderCreateMapper.toResponse(order);
    }
}
