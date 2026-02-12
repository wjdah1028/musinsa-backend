package com.shopping.musinsabackend.domain.order.dto.response;

import com.shopping.musinsabackend.domain.order.entity.OrderStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(title = "주문내역 응답 DTO")
public class OrderCreateResponse {

    @Schema(description = "주문 고유번호", example = "1")
    private Long orderId;

    @Schema(description = "주문 상태", example = "ORDER")
    private OrderStatus orderStatus;

    @Schema(description = "주문 시간", example = "2026-02-12:00000")
    private LocalDateTime orderAt;

    @Schema(description = "주문 가격", example = "50000")
    private Integer totalPrice;

    // 배송지 정보
    @Schema(description = "우편번호", example = "02356")
    private String zipcode;

    @Schema(description = "기본주소", example = "서울시 성북구 서경로")
    private String address;

    @Schema(description = "상세주소", example = "북악관")
    private String addressDetail;

    // 주문 목록
    @Schema(description = "주문 상품 리스트", example = "나이키 후드티, 나이키 바지..")
    private List<OrderItemResponse> orderItems;

}
