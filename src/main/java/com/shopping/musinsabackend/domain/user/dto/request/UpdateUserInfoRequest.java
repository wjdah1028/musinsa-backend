package com.shopping.musinsabackend.domain.user.dto.request;

import com.shopping.musinsabackend.domain.user.entity.Gender;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@Schema(title = "회원 정보 수정 Request DTO", description = "비밀번호 제외한 회원 정보 수정")
public class UpdateUserInfoRequest {
    
    // 이메일은 수정 못하게 방지 + 비밀번호는 다른 DTO 이용

    @Schema(description = "이름", example = "홍길동")
    @NotBlank(message = "이름은 필수 입력 값입니다.")
    private String name;

    @Schema(description = "생년월일", example = "2000-01-01")
    @NotNull(message = "생년월일은 필수 입력 값입니다.")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate age;

    @Schema(description = "성별", example = "MAN")
    @NotNull(message = "성별은 필수 입력 값입니다.")
    private Gender gender;

    @Schema(description = "전화번호", example = "010-0000-0000")
    @NotBlank(message = "전화번호는 필수 입력 값입니다.")
    @Pattern(regexp = "^\\d{2,3}-\\d{3,4}-\\d{4}$", message = "전화번호 형식이 올바르지 않습니다.")
    private String phone;

    @Schema(description = "우편번호", example = "00000")
    @NotBlank(message = "우편번호는 필수 입력 값입니다.")
    private String zipCode;

    @Schema(description = "기본 주소", example = "서울시 성북구 서경로")
    @NotBlank(message = "주소는 필수 입력 값입니다.")
    private String address;

    @Schema(description = "상세 주소", example = "서경대학교")
    @NotBlank(message = "상세 주소는 필수 입력 값입니다.")
    private String addressDetails;
}
