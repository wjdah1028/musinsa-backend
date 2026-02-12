package com.shopping.musinsabackend.domain.cart.controller;

import com.shopping.musinsabackend.domain.cart.dto.request.CartItemCreateRequest;
import com.shopping.musinsabackend.domain.cart.dto.response.CartItemCreateResponse;
import com.shopping.musinsabackend.domain.cart.service.CartService;
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

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/carts")
@Slf4j
@Tag(name = "Cart", description = "장바구니 API")
public class CartController {

    private final CartService cartService;

    @Operation(summary = "장바구니 상품 추가", description = "상품을 장바구니에 담는 API")
    @PostMapping("/contain-cart")
    public ResponseEntity<BaseResponse<CartItemCreateResponse>> containCart(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestBody @Valid CartItemCreateRequest request) {

        // CustomUserDetails에서 사용자 꺼냄
        UserEntity user = userDetails.getUser();

        log.info("로그인 유저 ID 확인: {}", user.getUserId());

        // 서비스 호출
        CartItemCreateResponse cartItemCreateResponse = cartService.addCart(user, request);

        // 응답 반환
        return ResponseEntity.ok(BaseResponse.success(200, "상품을 장바구니에 담았습니다.", cartItemCreateResponse));
    }

    @Operation(summary = "장바구니 상품 조회", description = "장바구니에 담긴 상품 조회 API")
    @GetMapping("/read-cart")
    public ResponseEntity<BaseResponse<List<CartItemCreateResponse>>> readCart(
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ) {
        // 사용자 꺼냄
        UserEntity user = customUserDetails.getUser();

        log.info("로그인 유저 ID: {}", user.getUserId());

        // 서비스 호출
        List<CartItemCreateResponse> responseList = cartService.getCartList(user);

        // 응답 반환
        return ResponseEntity.ok(BaseResponse.success(200, "장바구니 상품 조회 성공", responseList));
    }
}
