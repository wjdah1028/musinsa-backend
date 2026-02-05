package com.shopping.musinsabackend.domain.user.dto.request;

import com.shopping.musinsabackend.domain.user.entity.Gender;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

// 회원 가입
@Getter
@NoArgsConstructor
@Schema(title = "회원가입 요청 DTO", description = "회원가입할 때 필요한 데이터")
public class SignUpRequest {

    @Schema(description = "이메일", example = "test@test.com")
    @NotBlank(message = "이메일은 필수 입력값입니다.")
    @Email(message = "이메일 형식에 맞지 않습니다.")
    private String email;

    @Schema(description = "비밀번호", example = "password1122")
    @NotBlank(message = "비밀번호는 필수 입력값입니다.")
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,20}", message = "비밀번호는 영문 대,소문자, 숫자, 특수기호를 포함한 8~20자리여야 합니다.")
    private String password;

    @Schema(description = "이름", example = "홍길동")
    @NotBlank(message = "이름은 필수 입력 값입니다.")
    private String name;

    @Schema(description = "생년월일", example = "2000-01-01")
    @NotNull(message = "생년월일은 필수 입력 값입니다.")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate age;

    @Schema(description = "성별 (MAN/WOMAN)", example = "MAN")
    @NotNull(message = "성별은 필수 입력 값입니다.")
    private Gender gender;

    @Schema(description = "전화번호", example = "010-1111-1111")
    @NotBlank(message = "전화번호는 필수 입력 값입니다.")
    private String phone;

    @Schema(description = "우편번호", example = "02358")
    @NotBlank(message = "우편번호는 필수 입력 값입니다.")
    private String zipcode;

    @Schema(description = "기본 주소", example = "서울시 성북구 서경로 1123")
    @NotBlank(message = "주소는 필수 입력 값입니다.")
    private String address;

    @Schema(description = "상세 주소", example = "358호")
    @NotBlank(message = "상세 주소는 필수 입력 값입니다.")
    private String addressDetail;
}