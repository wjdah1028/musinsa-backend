package com.shopping.musinsabackend.domain.product.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(title = "카테고리 조회 응답 DTO")
public class CategoryReadResponse {

    @Schema(description = "카테고리 고유번호", example = "1")
    private Long categoryId;

    @Schema(description = "카테고리 이름", example = "상의")
    private String categoryName;

    @Schema(description = "카테고리 깊이", example = "2")
    private Integer depth;

    @Schema(description = "자식 카테고리 리스트")
    private List<CategoryReadResponse> children; // 자기 자신을 리스트로!
}
