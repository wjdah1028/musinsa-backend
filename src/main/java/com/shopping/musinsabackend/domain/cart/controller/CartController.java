package com.shopping.musinsabackend.domain.cart.controller;

import com.shopping.musinsabackend.domain.cart.dto.request.CartItemCreateRequest;
import com.shopping.musinsabackend.domain.cart.dto.response.CartItemCreateResponse;
import com.shopping.musinsabackend.domain.cart.service.CartService;
import com.shopping.musinsabackend.domain.user.entity.UserEntity;
import com.shopping.musinsabackend.global.response.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/carts")
@Tag(name = "Cart", description = "장바구니 API")
public class CartController {

    private final CartService cartService;

    @Operation(summary = "장바구니 상품 추가", description = "상품을 장바구니에 담는 API")
    @PostMapping("/contain-cart")
    public ResponseEntity<BaseResponse<CartItemCreateResponse>> containCart(
            @AuthenticationPrincipal UserEntity user,
            @RequestBody @Valid CartItemCreateRequest request) {

        // 서비스 호출
        CartItemCreateResponse cartItemCreateResponse = cartService.addCart(user, request);

        // 응답 반환
        return ResponseEntity.ok(BaseResponse.success(200, "상품을 장바구니에 담았습니다.", cartItemCreateResponse));
    }
}
