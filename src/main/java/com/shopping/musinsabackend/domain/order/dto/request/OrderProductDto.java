package com.shopping.musinsabackend.domain.order.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Schema(title = "주문할 상품 목록", description = "주문할 상품들이 담긴 데이터")
public class OrderProductDto {

    @Schema(description = "상품 아이디", example = "1")
    @NotNull(message = "상품 ID는 필수입니다.")
    private Long productId;

    @Schema(description = "각 상품 수량", example = "1")
    private Integer itemCount;
}
