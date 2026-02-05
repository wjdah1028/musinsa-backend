package com.shopping.musinsabackend.domain.user.controller;

import com.shopping.musinsabackend.domain.user.dto.request.SignUpRequest;
import com.shopping.musinsabackend.domain.user.dto.response.SignUpResponse;
import com.shopping.musinsabackend.domain.user.service.UserService;
import com.shopping.musinsabackend.global.response.BaseResponse;
import com.shopping.musinsabackend.global.security.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
@Tag(name = "User", description = "User 관련 API")
public class UserController {

    private final UserService userService;

    // 회원 가입 API
    @Operation(summary = "회원가입", description = "사용자 회원가입 API")
    @PostMapping("/sign-up")
    public ResponseEntity<BaseResponse<SignUpResponse>> signUp(@RequestBody @Valid SignUpRequest request) {

        // 서비스 호출
        SignUpResponse signUpResponse = userService.signUp(request);

        // 응답 반환
        return ResponseEntity.ok(BaseResponse.success(201, "회원가입에 성공했습니다.", signUpResponse));
    }

    // 회원 탈퇴 API
    @Operation(summary = "회원탈퇴", description = "사용자 탈퇴 API")
    @DeleteMapping("/delete-user")
    public ResponseEntity<BaseResponse<Void>> deleteUser(@AuthenticationPrincipal CustomUserDetails userDetails) {

        // 서비스 호출
        userService.deleteUser(userDetails.getUsername()); // CustomUserDetails를 보면 getUsername()을 호출하면 getEmail이 실행

        // 응답 반환
        return ResponseEntity.ok(BaseResponse.success(200, "회원 탈퇴가 완료되었습니다.", null));
    }
}

