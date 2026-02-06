package com.shopping.musinsabackend.global.config;

import com.shopping.musinsabackend.domain.user.entity.Gender;
import com.shopping.musinsabackend.domain.user.entity.Role;
import com.shopping.musinsabackend.domain.user.entity.UserEntity;
import com.shopping.musinsabackend.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Slf4j
@Component
@RequiredArgsConstructor
public class AdminInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {

        // 관리자 계정 있는지 확인
        if (!userRepository.existsByEmail("admin@naver.com")) {

            // 없으면 생성
            UserEntity admin = UserEntity.builder()
                    .email("admin@naver.com")
                    .password(passwordEncoder.encode("admin1234"))
                    .name("관리자")
                    .role(Role.ADMIN)
                    .phone("010-1234-5678")
                    .zipcode("00000")
                    .address("서울시 성북구 서경대학교")
                    .addressDetail("북악관")
                    .age(LocalDate.of(2002, 10, 28))
                    .gender(Gender.MAN)
                    .build();

            userRepository.save(admin);
            log.info("관리자 계정이 생성되었습니다. ID: {}, PW: {}", "admin@naver.com", "admin1234");
        }
    }
}
