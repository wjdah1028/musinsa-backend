package com.shopping.musinsabackend.domain.auth.dto.response;

import com.shopping.musinsabackend.domain.user.entity.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(title = "로그인 Response DTO", description = "사용자 로그인 데이터 응답")
public class LoginResponse {

    // 사용자 토큰
    @Schema(description = "사용자 발급 토큰")
    private String accessToken;

    // 사용자 생성 ID
    @Schema(description = "사용자 ID", example = "1")
    private Long userId;

    // 사용자 이메일
    @Schema(description = "사용자 email", example = "test45@naver.com")
    private String email;

    // 사용자 이름
    @Schema(description = "사용자 이름", example = "홍길동")
    private String name;

    // 사용자 권한
    @Schema(description = "사용자 권한", example = "USER")
    private Role role;

    // 토큰 만료시간
    @Schema(description = "사용자 토큰 만료시간", example = "1000000")
    private Long expiresAt;
}

