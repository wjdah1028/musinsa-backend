package com.shopping.musinsabackend.domain.product.dto.request;

import com.shopping.musinsabackend.domain.user.entity.Gender;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Schema(title = "상품 생성 DTO", description = "상품 등록을 위한 데이터")
public class ProductCreateRequest {

    @Schema(description = "상품 이름", example = "데일리 헨리넥 니트")
    @NotBlank(message = "상품 이름은 필수 입력 값입니다.")
    private String productName;

    @Schema(description = "상품 상세 설명", example = "매일 입기 좋은 베이직한 데일리 헨리넥 니트입니다. 사시와 헤라시 디테일을 적용해 편안한 착용감과 자연스러운 핏을 제공합니다. 캐주얼한 실루엣으로 단독 착용은 물론, 이너로 레이어드해도 스타일링이 가능합니다.")
    private String productContent;

    @Schema(description = "상품 가격", example = "29900")
    @NotNull(message = "상품 가격은 필수 입력 값입니다.")
    private int price;

    @Schema(description = "상품 재고", example = "150")
    @NotNull(message = "상품 재고는 필수 입력 값입니다.")
    private int stock;

    @Schema(description = "상품 추천 성별", example = "MAN")
    @NotNull(message = "상품 추천 성별은 필수 입력 값입니다.")
    private Gender gender;

    @Schema(description = "상품 브랜드", example = "수아레")
    @NotNull(message = "상품 브랜드 이름은 필수 입력 값입니다.")
    private Long brandId;

    @Schema(description = "상품 카테고리", example = "상의")
    @NotNull(message = "상품 카테고리는 필수 입력 값입니다.")
    private Long categoryId;
}
