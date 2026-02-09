package com.shopping.musinsabackend.domain.product.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Schema(title = "카테고리 생성 DTO", description = "카테고리 등록할때 필요한 데이터")
public class CategoryCreateRequest {

    @Schema(description = "카테고리 이름", example = "남성")
    @NotBlank(message = "카테고리 이름은 필수 입력 값입니다.")
    private String categoryName;
}
