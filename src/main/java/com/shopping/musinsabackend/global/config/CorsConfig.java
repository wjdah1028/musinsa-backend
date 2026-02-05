package com.shopping.musinsabackend.global.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
public class CorsConfig {

    // application.properties에서 설정을 읽어옵니다. (콤마로 구분된 여러 주소)
    @Value("${cors.allowed-origins}")
    private String[] allowedOrigins;

    @Bean
    public UrlBasedCorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        // 1. 허용할 출처(Origin) 설정 - yml 파일에서 읽어온 값 적용
        // 리스트로 변환하여 적용 (Arrays.asList 사용)
        configuration.setAllowedOrigins(Arrays.asList(allowedOrigins));

        // 2. 허용할 HTTP 메서드 설정
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));

        // 3. 허용할 헤더 설정
        // 🚨 중요: JWT 로그인을 하려면 "Authorization" 헤더를 반드시 허용해야 합니다!
        configuration.setAllowedHeaders(Arrays.asList("Content-Type", "Accept", "Authorization"));

        // 4. 자격 증명(Cookie, Header 등) 허용 설정
        // 프론트엔드에서 토큰을 주고받을 때 true여야 합니다.
        configuration.setAllowCredentials(true);

        // 5. 브라우저가 preflight 요청 결과를 캐싱하는 시간 (선택사항, 예: 1시간)
        configuration.setMaxAge(3600L);

        // 모든 경로("/**")에 대해 위 설정을 적용
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}