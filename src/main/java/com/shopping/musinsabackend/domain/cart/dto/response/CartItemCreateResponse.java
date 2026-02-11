package com.shopping.musinsabackend.domain.cart.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(title = "장바구니 응답 DTO")
public class CartItemCreateResponse {

    @Schema(description = "장바구니 상품 고유 ID", example = "1")
    private Long cartItemId;

    @Schema(description = "상품 ID", example = "1")
    private Long productId;

    @Schema(description = "상품 이름", example = "맨투맨")
    private String productName;

    @Schema(description = "상품 이미지 URL", example = "http://..")
    private String imageUrl; // 사진 하나 보여줄거라 imageUrl

    @Schema(description = "담은 수량", example = "2")
    private Integer itemCount;

    @Schema(description = "총 가격", example = "20000")
    private Integer totalPrice;
}
