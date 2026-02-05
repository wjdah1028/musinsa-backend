package com.shopping.musinsabackend.domain.auth.service;

import com.shopping.musinsabackend.domain.auth.dto.request.LoginRequest;
import com.shopping.musinsabackend.domain.auth.dto.response.LoginResponse;
import com.shopping.musinsabackend.domain.auth.mapper.AuthMapper;
import com.shopping.musinsabackend.domain.user.entity.UserEntity;
import com.shopping.musinsabackend.domain.user.exception.UserErrorCode;
import com.shopping.musinsabackend.domain.user.repository.UserRepository;
import com.shopping.musinsabackend.global.exception.CustomException;
import com.shopping.musinsabackend.global.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    private final UserRepository userRepository;
    private final AuthMapper authMapper;

    // 로그인 로직
    @Transactional
    public LoginResponse login(LoginRequest loginRequest) {
        // 1. 유저 확인
        // 🚨 수정됨: User -> UserEntity
        UserEntity user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new CustomException(UserErrorCode.USER_NOT_FOUND));

        // 2. 인증 토큰 생성 (ID/PW)
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(), loginRequest.getPassword());

        // 3. 인증 처리 (여기서 비밀번호 검증이 일어남)
        authenticationManager.authenticate(authenticationToken);

        // 4. 액세스 토큰 및 리프레시 토큰 발급
        // 🚨 수정됨: user.getRole()이 Enum이면 .toString() 붙이기, 아니면 그대로 사용
        String accessToken = jwtProvider.createAccessToken(
                user.getEmail(), user.getRole().toString(), "custom");

        String refreshToken = jwtProvider.createRefreshToken(
                user.getEmail(), UUID.randomUUID().toString());

        // 5. 리프레시 토큰 DB에 저장 (UserEntity 엔티티에 메서드 필요)
        user.createRefreshToken(refreshToken);

        // 6. Access Token의 만료 시간을 가져옴
        Long expirationTime = jwtProvider.getExpiration(accessToken);

        // 7. 로그인 성공 로깅
        log.info("로그인 성공: {}", user.getEmail());

        // 8. 로그인 응답 반환
        return authMapper.toLoginResponse(user, accessToken, expirationTime);
    }

    // 로그아웃 로직
    @Transactional
    public void logout(String token) {

        // 토큰에서 Bearer 제거 후 이메일 추출
        String resolvedToken = token.substring(7);
        String email = jwtProvider.extractSocialId(resolvedToken);

        // DB에서 유저 찾기
        UserEntity user = userRepository.findByEmail(email).orElseThrow(() -> new CustomException(UserErrorCode.USER_NOT_FOUND));

        // 리프레시 토큰 삭제 => Null로 업데이트 함
        user.expireRefreshToken();

        log.info("로그아웃 성공: {}", email);
    }
}