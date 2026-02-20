package com.shopping.musinsabackend.domain.order.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(title = "주문 상품 리스트")
public class OrderItemResponse {

    @Schema(description = "상품 고유번호", example = "1")
    private Long productId;

    @Schema(description = "상품 이름", example = "나이키 후드티")
    private String productName;

    @Schema(description = "상품 브랜드", example = "나이키")
    private String brandName;

    @Schema(description = "주문 당시 가격", example = "29900")
    private Integer orderPrice;

    @Schema(description = "주문 당시 수량", example = "1")
    private Integer orderCount;

    @Schema(description = "해당 상품 총 가격", example = "50000")
    private Integer totalPrice;

    @Schema(description = "상품 썸네일 사진", example = "https://..")
    private String imageUrl;
}
