package com.shopping.musinsabackend.domain.order.controller;

import com.shopping.musinsabackend.domain.order.dto.request.OrderCreateRequest;
import com.shopping.musinsabackend.domain.order.dto.response.OrderDetailResponse;
import com.shopping.musinsabackend.domain.order.service.OrderService;
import com.shopping.musinsabackend.domain.user.entity.UserEntity;
import com.shopping.musinsabackend.global.response.BaseResponse;
import com.shopping.musinsabackend.global.security.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
@Slf4j
@Tag(name = "Order", description = "주문 관련 API")
public class OrderController {

    private final OrderService orderService;

    @Operation(summary = "주문 생성", description = "상품 주문하는 API")
    @PostMapping("/order-create")
    public ResponseEntity<BaseResponse<OrderDetailResponse>> createOrder(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestBody @Valid OrderCreateRequest request
    ) {

        // 로그인된 사용자 정보
        UserEntity user = userDetails.getUser();

        log.info("주문 요청 들어옴 - User: {}", user.getEmail());

        // 서비스 호출
        OrderDetailResponse response = orderService.createOrder(user, request);

        // 응답 반환
        return ResponseEntity.ok(BaseResponse.success(200, "상품 주문에 성공했습니다.", response));
    }

    @Operation(summary = "주문 상세 조회", description = "주문 상세 조회 API")
    @GetMapping("/order/{orderId}")
    public ResponseEntity<BaseResponse<OrderDetailResponse>> getOrderDetail(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable @Valid Long orderId
    ) {
        UserEntity user = userDetails.getUser();

        // 서비스 호출
        OrderDetailResponse response = orderService.getOrderDetail(orderId, user);

        // 응답 반환
        return ResponseEntity.ok(BaseResponse.success(200, "주문 상세 조회 성공", response));
    }
}
