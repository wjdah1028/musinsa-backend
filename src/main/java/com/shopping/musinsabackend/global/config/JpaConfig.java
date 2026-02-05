package com.shopping.musinsabackend.global.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing // JPA Auditing 기능 활성화 => 날짜가 자동으로 찍힘
public class JpaConfig {
}