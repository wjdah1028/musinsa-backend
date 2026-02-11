package com.shopping.musinsabackend.domain.cart.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Schema(title = "장바구니 등록 요청 DTO", description = "상품을 장바구니에 담을 때 필요한 데이터")
public class CartItemCreateRequest {

    @Schema(description = "장바구니에 넣을 상품의 고유 ID", example = "1")
    @NotNull(message = "상품 ID는 필수 입력 값입니다.")
    private Long productId;

    @Schema(description = "장바구니에 넣을 수량", example = "1")
    @NotNull(message = "수량 선택은 필수 입니다.")
    private Integer itemCount;
}
