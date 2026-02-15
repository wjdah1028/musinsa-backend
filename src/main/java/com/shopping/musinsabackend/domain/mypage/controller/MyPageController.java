package com.shopping.musinsabackend.domain.mypage.controller;

import com.shopping.musinsabackend.domain.mypage.dto.MyPageResponse;
import com.shopping.musinsabackend.domain.mypage.service.MyPageService;
import com.shopping.musinsabackend.domain.user.entity.UserEntity;
import com.shopping.musinsabackend.global.response.BaseResponse;
import com.shopping.musinsabackend.global.security.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/mypage")
@Tag(name = "MyPage", description = "마이 페이지 조회")
public class MyPageController {

    private final MyPageService myPageService;

    @Operation(summary = "마이페이지 조회", description = "마이페이지 조회 API")
    @GetMapping("/mypage-read")
    public ResponseEntity<BaseResponse<MyPageResponse>> readMyPage(
            @AuthenticationPrincipal CustomUserDetails userDetails
        ) {

        // 사용자 호출
        UserEntity user = userDetails.getUser();

        // 서비스 호출
        MyPageResponse response = myPageService.myPage(user);

        // 응답 반환
        return ResponseEntity.ok(BaseResponse.success(200, "마이페이지 정보 조회 성공", response));
    }
}
