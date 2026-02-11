package com.shopping.musinsabackend.domain.cart.exception;

import com.shopping.musinsabackend.global.exception.model.BaseErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CartErrorCode implements BaseErrorCode {
    CART_NOT_FOUND(HttpStatus.NOT_FOUND, "장바구니를 찾을 수 없습니다."),
    INVALID_QUANTITY(HttpStatus.BAD_REQUEST, "수량은 1개 이상이어야 합니다."),
    ITEM_NOT_FOUND(HttpStatus.NOT_FOUND, "장바구니에 해당 상품이 없습니다."),
    EMPTY_CART(HttpStatus.NOT_FOUND, "장바구니에 담은 상품이 없습니다. "); // 장바구니 전체 조회할 때 사용

    private final HttpStatus httpStatus;
    private final String message;

    @Override
    public String getCode() {
        return name();
    }

    @Override
    public HttpStatus getStatus() {
        return this.httpStatus;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
