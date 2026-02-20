package com.shopping.musinsabackend.domain.product.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(title = "카테고리 등록 응답 DTO")
public class CategoryCreateResponse {

    @Schema(description = "카테고리 고유번호", example = "1")
    private Long categoryId;

    @Schema(description = "카테고리 이름", example = "상의")
    private String categoryName;
}
