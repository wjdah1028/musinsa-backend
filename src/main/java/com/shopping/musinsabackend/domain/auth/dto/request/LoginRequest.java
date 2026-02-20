package com.shopping.musinsabackend.domain.auth.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(title = "로그인 Request DTO", description = "사용자 로그인을 위한 데이터 전송")
public class LoginRequest {

    // 이메일로 로그인
    @Schema(description = "사용자 아이디", example = "wjdah1028@naver.com")
    @NotBlank(message = "이메일은 필수 입력값입니다.")
    private String email;

    // 비밀번호로 로그인
    @Schema(description = "사용자 비밀번호", example = "c*114455678")
    @NotBlank(message = "비밀번호는 필수 입력값입니다.")
    private String password;
}
