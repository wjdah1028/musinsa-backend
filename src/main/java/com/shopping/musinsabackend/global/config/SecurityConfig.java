package com.shopping.musinsabackend.global.config;

import com.shopping.musinsabackend.global.security.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CorsConfig corsConfig;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    // 👇 아직 파일이 없으므로 주석 처리 (나중에 소셜 로그인 구현 시 주석 해제)
    // private final CustomOAuth2UserService oauth2UserService;
    // private final OAuth2LoginSuccessHandler customSuccessHandler;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // 1. CSRF 해제 (JWT 사용 시 필요 없음)
                .csrf(AbstractHttpConfigurer::disable)

                // 2. CORS 설정 (CorsConfig 파일 사용)
                .cors(cors -> cors.configurationSource(corsConfig.corsConfigurationSource()))

                // 3. HTTP Basic 인증 설정 (필요 시 유지, 안 쓰면 disable 해도 됨)
                .httpBasic(AbstractHttpConfigurer::disable)

                // 4. ★ 세션 사용 안 함 설정 (JWT 핵심!) ★
                // 서버가 세션을 생성하지도 않고, 기존 세션을 사용하지도 않음 -> 완전한 Stateless
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // 5. 요청 URL별 권한 설정
                .authorizeHttpRequests(request -> request
                        // Swagger 허용
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()

                        // AuthController 허용 (로그인 등)
                        .requestMatchers("/api/auths/**").permitAll()

                        // UserController의 회원가입 주소 허용
                        .requestMatchers("/api/users/sign-up").permitAll()

                        // 나머지 요청은 인증 필요
                        .anyRequest().authenticated()
                )

                // 6. JWT 필터 등록 (UsernamePasswordFilter 앞에 실행)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        // 7. OAuth2 로그인 설정 (나중에 구현 시 주석 해제)
            /*
            .oauth2Login(oauth2 -> oauth2
                .userInfoEndpoint(userInfo -> userInfo.userService(oauth2UserService)) // 사용자 정보 처리
                .successHandler(customSuccessHandler) // 로그인 성공 후 토큰 발급 처리
            );
            */

        return http.build();
    }

    // 비밀번호 암호화 빈
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 인증 매니저 빈
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}