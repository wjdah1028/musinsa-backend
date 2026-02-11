package com.shopping.musinsabackend.domain.product.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(title = "상품 정보 조회 응답 DTO")
public class ProductReadResponse {

    @Schema(description = "상품 고유번호", example = "1")
    private Long productId;

    @Schema(description = "상품 이름", example = "데일리 니트")
    private String productName;

    @Schema(description = "상품 가격", example = "29900")
    private int price;

    @Schema(description = "브랜드 이름", example = "수아레")
    private String brandName;

    @Schema(description = "카테고리 이름", example = "맨투맨")
    private String categoryName;

    @Schema(description = "상품 이미지 URL 리스트", example = "[\"https://s3...\", \"https://s3...\"]")
    private List<String> imageUrls;
}
