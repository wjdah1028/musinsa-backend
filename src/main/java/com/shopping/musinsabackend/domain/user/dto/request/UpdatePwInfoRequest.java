package com.shopping.musinsabackend.domain.user.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Schema(title = "비밀번호 수정 Request DTO", description = "비밀번호 수정")
public class UpdatePwInfoRequest {

    @Schema(description = "현재 비밀번호", example = "c*1123462")
    @NotBlank(message = "현재 비밀번호를 입력해주세요.")
    private String password;

    @Schema(description = "새 비밀번호", example = "c*1587864")
    @NotBlank(message = "새로운 비밀번호를 입력해주세요.")
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,20}", message = "비밀번호는 영문 대,소문자, 숫자, 특수기호를 포함한 8~20자리여야 합니다.")
    private String newPassword;
}
