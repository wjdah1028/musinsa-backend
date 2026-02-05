package com.shopping.musinsabackend.domain.auth.controller;

import com.shopping.musinsabackend.domain.auth.dto.request.LoginRequest;
import com.shopping.musinsabackend.domain.auth.dto.response.LoginResponse;
import com.shopping.musinsabackend.domain.auth.service.AuthService;
import com.shopping.musinsabackend.domain.user.exception.UserErrorCode;
import com.shopping.musinsabackend.domain.user.repository.UserRepository;
import com.shopping.musinsabackend.global.exception.CustomException;
import com.shopping.musinsabackend.global.jwt.JwtProvider;
import com.shopping.musinsabackend.global.response.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auths") // 참고 코드의 경로 따름
@Tag(name = "Auth", description = "Auth 관리 API")
public class AuthController {

    private final AuthService authService;
    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;

    @Operation(summary = "사용자 로그인", description = "사용자 로그인을 위한 API")
    @PostMapping("/login")
    public ResponseEntity<BaseResponse<LoginResponse>> login(
            @RequestBody @Valid LoginRequest loginRequest, HttpServletResponse response) {

        // 1. 서비스 로직 실행
        LoginResponse loginResponse = authService.login(loginRequest);

        // 2. refreshToken 가져오기 (Repository 직접 접근 - 참고 코드 스타일)
        String refreshToken = userRepository
                .findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new CustomException(UserErrorCode.USER_NOT_FOUND))
                .getRefreshToken(); // User 엔티티에 @Getter가 있어야 함

        // 3. Set-Cookie 설정 (HttpOnly + Secure)
        // 7일 = 60 * 60 * 24 * 7
        jwtProvider.addJwtToCookie(response, refreshToken, "refreshToken", 60 * 60 * 24 * 7);

        return ResponseEntity.ok(BaseResponse.success(200, "로그인에 성공했습니다.", loginResponse));
    }

    @Operation(summary = "사용자 로그아웃", description = "사용자 로그아웃을 위한 API")
    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestHeader("Authorization") String token) {
        authService.logout(token);
        return ResponseEntity.ok("로그아웃 되었습니다.");
    }
}