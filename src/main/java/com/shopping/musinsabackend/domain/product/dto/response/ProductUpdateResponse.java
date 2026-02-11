package com.shopping.musinsabackend.domain.product.dto.response;


import com.shopping.musinsabackend.domain.user.entity.Gender;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(title = "상품 수정 응답 DTO")
public class ProductUpdateResponse {
    @Schema(description = "상품 고유번호", example = "1")
    private Long productId;

    @Schema(description = "상품 이름", example = "데일리 헨리넥 니트")
    private String productName;

    @Schema(description = "상품 설명", example = "매일 입기 좋은 베이직한 데일리 헨리넥 니트입니다. 사시와 헤라시 디테일을 적용해 편안한 착용감과 자연스러운 핏을 제공합니다. 캐주얼한 실루엣으로 단독 착용은 물론, 이너로 레이어드해도 스타일링이 가능합니다.")
    private String productContent;

    @Schema(description = "상품 가격", example = "29900")
    private Integer price;

    @Schema(description = "상품 재고", example = "150")
    private Integer stock;

    @Schema(description = "상품 추천 성별", example = "MAN")
    private Gender gender;

    @Schema(description = "상품 이미지 URL", example = "https://s3...")
    private List<String> imageUrls;

    @Schema(description = "상품 리뷰 개수", example = "0")
    private int reviewCount;

    @Schema(description = "상품 좋아요 개수", example = "0")
    private int productLike;

    @Schema(description = "상품 브랜드 이름", example = "수아레")
    private String brandName;

    @Schema(description = "상품 카테고리 이름", example = "상의")
    private String categoryName;

    @Schema(description = "상품 수정 일시", example = "2026-02-09T10:00:00")
    private LocalDateTime updatedAt;
}
