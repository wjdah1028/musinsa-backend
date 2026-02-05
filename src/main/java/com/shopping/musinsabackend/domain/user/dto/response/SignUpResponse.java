package com.shopping.musinsabackend.domain.user.dto.response;

import com.shopping.musinsabackend.domain.user.entity.Gender;
import com.shopping.musinsabackend.domain.user.entity.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDate;

// 회원가입
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(title = "회원가입 응답 DTO")
public class SignUpResponse {

    @Schema(description = "회원 고유 ID", example = "1")
    private Long userId;

    @Schema(description = "이메일", example = "test@test.com")
    private String email;

    @Schema(description = "이름", example = "홍길동")
    private String name;

    @Schema(description = "생년월일", example = "2000-01-01")
    private LocalDate age;

    @Schema(description = "성별", example = "MAN")
    private Gender gender;

    @Schema(description = "전화번호", example = "010-1111-1111")
    private String phone;

    @Schema(description = "우편번호", example = "02354")
    private String zipcode;

    @Schema(description = "기본 주소", example = "서울시 성북구 서경로 1234")
    private String address;

    @Schema(description = "상세 주소", example = "102동 125호")
    private String addressDetail;

    @Schema(description = "권한", example = "USER")
    private Role role;

}