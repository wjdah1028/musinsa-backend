package com.shopping.musinsabackend.domain.mypage.service;

import com.shopping.musinsabackend.domain.mypage.dto.MyPageResponse;
import com.shopping.musinsabackend.domain.mypage.mapper.MyPageMapper;
import com.shopping.musinsabackend.domain.order.entity.OrderEntity;
import com.shopping.musinsabackend.domain.order.repository.OrderRepository;
import com.shopping.musinsabackend.domain.user.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class MyPageService {

    private final OrderRepository orderRepository;
    private final MyPageMapper myPageMapper;

    @Transactional
    public MyPageResponse myPage(UserEntity user) {

        // 유저 주문내역 가져오기
        List<OrderEntity> orders = orderRepository.findAllByUserOrderByOrderAtDesc(user);

        // 반환
        return myPageMapper.toResponse(user, orders);
    }
}
