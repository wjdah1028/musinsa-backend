package com.shopping.musinsabackend.domain.order.exception;

import com.shopping.musinsabackend.global.exception.model.BaseErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum OrderErrorCode implements BaseErrorCode {
    ORDER_NOT_FOUND("ORD4001", HttpStatus.NOT_FOUND, "주문을 찾을 수 없습니다."),
    EMPTY_ORDER_ITEMS("ORD4002", HttpStatus.BAD_REQUEST, "주문할 상품이 없습니다."),
    STOCK_EMPTY("ORD4003", HttpStatus.NOT_FOUND, "품절인 상품입니다."),
    ORDER_NOT_USER("ORD4004", HttpStatus.BAD_REQUEST, "사용자와 일치하지 않습니다."),
    ALREADY_CANCEL("ORD4005", HttpStatus.BAD_REQUEST, "이미 취소된 주문입니다.");

    private final String code;
    private final HttpStatus status;
    private final String message;
}
