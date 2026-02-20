package com.shopping.musinsabackend.domain.order.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@Schema(title = "주문 생성 요청 DTO", description = "주문 생성할 때 필요한 데이터")
public class OrderCreateRequest {

    @Schema(description = "우편번호", example = "02354")
    @NotBlank(message = "우편번호는 필수 입력 값입니다.")
    private String zipcode;

    @Schema(description = "기본주소", example = "서울시 성북구 서경로")
    @NotBlank(message = "기본주소는 필수 입력 값입니다.")
    private String address;

    @Schema(description = "상세주소", example = "북악관")
    @NotBlank(message = "상세주소는 필수 입력 값입니다.")
    private String addressDetail;

    @Schema(description = "주문 목록")
    @NotEmpty(message = "주문할 상품이 1개 이상이어야 합니다.")
    @Valid
    private List<OrderProductDto> orderProducts;
}
