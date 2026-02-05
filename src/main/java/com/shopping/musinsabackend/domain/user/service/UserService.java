package com.shopping.musinsabackend.domain.user.service;

import com.shopping.musinsabackend.domain.user.dto.request.SignUpRequest;
import com.shopping.musinsabackend.domain.user.dto.response.InfoResponse;
import com.shopping.musinsabackend.domain.user.dto.response.SignUpResponse;
import com.shopping.musinsabackend.domain.user.entity.UserEntity;
import com.shopping.musinsabackend.domain.user.exception.UserErrorCode;
import com.shopping.musinsabackend.domain.user.mapper.InfoMapper;
import com.shopping.musinsabackend.domain.user.mapper.SignUpMapper;
import com.shopping.musinsabackend.domain.user.repository.UserRepository;
import com.shopping.musinsabackend.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class UserService {

    private final UserRepository userRepository; // DB랑 대화하는 도구
    private final SignUpMapper signUpMapper; // DTO -> Entity
    private final PasswordEncoder passwordEncoder; // 비밀번호 암호화
    private final InfoMapper infoMapper;

    // 회원가입 로직
    @Transactional
    public SignUpResponse signUp(SignUpRequest request) {

        // 중복 이메일 검사
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new CustomException(UserErrorCode.DUPLICATE_EMAIL);
        }

        // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(request.getPassword());

        // DTO -> Entity
        UserEntity userEntity = signUpMapper.toEntity(request, encodedPassword);

        // DB에 저장
        UserEntity savedUser = userRepository.save(userEntity);

        // 로그 남기기
        log.info("새로운 회원 가입 성공: email={}", savedUser.getEmail());

        // Entity -> Response
        return signUpMapper.toResponse(savedUser);
    }

    // 회원 탈퇴
    @Transactional
    public void deleteUser(String email) {
        // 유저 조회
        // UserEntity 객체 user 생성
        UserEntity user = userRepository.findByEmail(email).orElseThrow(() -> new CustomException(UserErrorCode.USER_NOT_FOUND));

        // 유저 삭제
        userRepository.delete(user);

        log.info("회원 탈퇴 완료 : email={}", email);
    }

    // 회원 정보 조회
    public InfoResponse infoUser(String email) {

        // 유저 조회
        UserEntity user = userRepository.findByEmail(email).orElseThrow(() -> new CustomException(UserErrorCode.USER_NOT_FOUND));

        // Entity -> DTO 변환 후 반환
        return infoMapper.toResponse(user);
    }
}
