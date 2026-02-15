package com.shopping.musinsabackend.domain.mypage.mapper;

import com.shopping.musinsabackend.domain.mypage.dto.MyPageResponse;
import com.shopping.musinsabackend.domain.order.dto.response.OrderPastResponse;
import com.shopping.musinsabackend.domain.order.entity.OrderEntity;
import com.shopping.musinsabackend.domain.order.entity.OrderStatus;
import com.shopping.musinsabackend.domain.user.entity.UserEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class MyPageMapper {

    public MyPageResponse toResponse(UserEntity user, List<OrderEntity> orders) {

        // 현재 진행중인 주문 개수
        long activeOrder = orders.stream().filter(o -> o.getOrderStatus() == OrderStatus.ORDER).count();

        // 취소된 주문 개수
        long cancelOrder = orders.stream().filter(o -> o.getOrderStatus() == OrderStatus.CANCEL).count();

        // 최근 주문 10개 뽑기
        List<OrderPastResponse> recentOrder = orders.stream()
                .limit(10)
                .map(OrderPastResponse::from)
                .collect(Collectors.toList());

        return MyPageResponse.builder()
                .email(user.getEmail())
                .name(user.getName())
                .totalOrder(orders.size())
                .activeOrder(activeOrder)
                .cancelOrder(cancelOrder)
                .recentOrder(recentOrder)
                .build();
    }
}
