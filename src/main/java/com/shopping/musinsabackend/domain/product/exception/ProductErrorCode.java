package com.shopping.musinsabackend.domain.product.exception;

import com.shopping.musinsabackend.global.exception.model.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ProductErrorCode implements BaseErrorCode {

    // 상품 관련 에러
    PRODUCT_NOT_FOUND("PROD4001", "존재하지 않는 상품입니다.", HttpStatus.NOT_FOUND),
    PRODUCT_ALREADY_EXIST("PROD4006", "이미 존재하는 상품입니다.",  HttpStatus.CONFLICT),
    PRODUCT_ALL_EXIST("PRO4007", "상품이 존재하지 않습니다.", HttpStatus.NOT_FOUND),

    // 브랜드 관련 에러
    BRAND_NOT_FOUND("PROD4002", "존재하지 않는 브랜드입니다.", HttpStatus.BAD_REQUEST),
    BRAND_ALREADY_EXIST("PROD4004", "이미 존재하는 브랜드입니다.", HttpStatus.CONFLICT),

    // 카테고리 관련 에러
    CATEGORY_NOT_FOUND("PROD4003", "존재하지 않는 카테고리입니다.", HttpStatus.BAD_REQUEST),
    CATEGORY_ALREADY_EXIST("PROD4005", "이미 존재하는 카테고리입니다.", HttpStatus.CONFLICT);

    private final String code;
    private final String message;
    private final HttpStatus status;
}
