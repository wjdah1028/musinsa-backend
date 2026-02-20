package com.shopping.musinsabackend.domain.user.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.shopping.musinsabackend.global.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class UserEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 자동 증가
    private Long userId; // 사용자 고유번호

    @Column(nullable = false)
    private String name; // 이름

    @Column(length = 10)
    private String zipcode; // 우편 번호

    @Column(length = 300, nullable = false)
    private String address; // 기본 주소

    @Column(length = 100)
    private String addressDetail; // 상세 주소

    @Column(nullable = false)
    private LocalDate age; // 나이 -> int로 하면 년생이 아닌 가입 당시 나이로만 저장이 되기 떄문에 => LocalDateTime

    @Enumerated(EnumType.STRING)
    @Column(length = 10, nullable = false)
    private Gender gender; // 성별 -> String형으로 하면 사용자가 서로 다르게 입력할 수 있기 때문에 Enum으로 통일하는 것이 좋음

    @Column(length = 300, nullable = false, unique = true)
    private String email; // 이메일 -> 회원가입할 때 아이디가 될 예정

    @Column(length = 300, nullable = false)
    private String password; // 비밀번호

    @Column(length = 50, nullable = false)
    private String phone; // 전화번호

    @Enumerated(EnumType.STRING)
    @Column(length = 20, nullable = false)
    private Role role; // 권한

    @JsonIgnore
    @Column(name = "refresh_token")
    private String refreshToken; // 토큰 저장 필드

    // 리프레시 토큰 업데이트 메서드
    public void createRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    // 로그아웃 시 리프레시 토큰 null로 변경
    public void expireRefreshToken() {
        this.refreshToken = null;
    }

    // 기본적인 회원 정보 수정
    public void updateUserInfo(String name, LocalDate age, Gender gender, String phone, String zipcode, String address, String addressDetail) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.phone = phone;
        this.zipcode = zipcode;
        this.address = address;
        this.addressDetail = addressDetail;
    }

    // 비밀번호 변경
    public void updateUserPw(String encryptedPassword) {
        this.password = encryptedPassword;
    }
}
