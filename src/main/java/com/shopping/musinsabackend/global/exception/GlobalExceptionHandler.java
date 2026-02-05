package com.shopping.musinsabackend.global.exception;

import com.shopping.musinsabackend.domain.auth.exception.AuthErrorCode;
import com.shopping.musinsabackend.global.exception.model.BaseErrorCode;
import com.shopping.musinsabackend.global.response.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    // 커스텀 예외
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<BaseResponse<Object>> handleCustomException(CustomException ex) {
        BaseErrorCode errorCode = ex.getErrorCode();
        log.error("Custom 오류 발생: {}", ex.getMessage());
        return ResponseEntity.status(errorCode.getStatus())
                .body(BaseResponse.error(errorCode.getStatus().value(), ex.getMessage()));
    }

    // Validation 실패
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<BaseResponse<Object>> handleValidationException(
            MethodArgumentNotValidException ex) {
        String errorMessages =
                ex.getBindingResult().getFieldErrors().stream()
                        .map(e -> String.format("[%s] %s", e.getField(), e.getDefaultMessage()))
                        .collect(Collectors.joining(" / "));
        log.warn("Validation 오류 발생: {}", errorMessages);
        return ResponseEntity.badRequest().body(BaseResponse.error(400, errorMessages));
    }

    // 예상치 못한 예외
    @ExceptionHandler(Exception.class)
    public ResponseEntity<BaseResponse<Object>> handleException(Exception ex) {
        log.error("Server 오류 발생: ", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(BaseResponse.error(500, "예상치 못한 서버 오류가 발생했습니다."));
    }

    // 비밀번호/아이디 불일치 예외 처리
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<BaseResponse<Void>> handleBadCredentialsException() {
        // 1. 우리가 만든 에러 코드 가져오기
        AuthErrorCode errorCode = AuthErrorCode.INVALID_PASSWORD;

        // 2. BaseResponse 형식으로 변환해서 반환
        return ResponseEntity
                .status(errorCode.getStatus()) // HTTP 상태 코드 (401)
                .body(BaseResponse.error(
                        errorCode.getStatus().value(), // 코드 (401)
                        errorCode.getMessage()         // 메시지 ("아이디 또는 비밀번호가...")
                ));
    }

    // 토큰이 누락되었을 때 발생하는 에러 처리
    @ExceptionHandler(org.springframework.web.bind.MissingRequestHeaderException.class)
    public ResponseEntity<BaseResponse<Void>> handleMissingRequestHeaderException(Exception e) {
        return ResponseEntity
                .badRequest()
                .body(BaseResponse.error(400, "필수 헤더 정보(Authorization)가 누락되었습니다."));
    }
}
