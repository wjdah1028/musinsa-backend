package com.shopping.musinsabackend.domain.mypage.dto;

import com.shopping.musinsabackend.domain.order.dto.response.OrderPastResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(title = "마이페이지 응답 DTO")
public class MyPageResponse {

    @Schema(description = "회원 이메일", example = "wjdah1028@skuniv.ac.kr")
    private String email;

    @Schema(description = "회원 이름", example = "홍길동")
    private String name;

    @Schema(description = "총 주문 횟수", example = "2")
    private long totalOrder;

    @Schema(description = "진행중인 주문", example = "1")
    private long activeOrder;

    @Schema(description = "취소된 주문", example = "1")
    private long cancelOrder;

    @Schema(description = "최근 주문 내역")
    private List<OrderPastResponse> recentOrder;

    @Schema(description = "AI 코디 추천")
    private String aiRecommendation;
}
