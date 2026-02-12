package com.shopping.musinsabackend.domain.cart.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Schema(title = "장바구니 상품 수정 Request DTO", description = "장바구니에 담긴 상품 개수 수정")
public class CartItemUpdateRequest {

    @Schema(description = "변경할 수량", example = "1")
    @NotNull(message = "수량은 필수 입력 값입니다.")
    private Integer itemCount;
}
