package com.shopping.musinsabackend.domain.product.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Schema(title = "브랜드 생성 DTO", description = "브랜드 등록할 때 필요한 데이터")
public class BrandCreateRequest {

    @Schema(description = "브랜드 이름", example = "나이키")
    @NotBlank(message = "브랜드 이름은 필수 입력 값입니다.")
    private String brandName;
}
