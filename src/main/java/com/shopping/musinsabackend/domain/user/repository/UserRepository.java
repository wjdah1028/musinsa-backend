package com.shopping.musinsabackend.domain.user.repository;

import com.shopping.musinsabackend.domain.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    // 이메일로 아이디 조회하기
    Optional<UserEntity> findByEmail(String email);

    // 이메일 중복 체크하기
    boolean existsByEmail(String email);
}
