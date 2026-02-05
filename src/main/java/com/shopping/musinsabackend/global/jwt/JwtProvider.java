package com.shopping.musinsabackend.global.jwt;

import com.shopping.musinsabackend.domain.auth.exception.AuthErrorCode;
import com.shopping.musinsabackend.global.exception.CustomException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Base64;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Slf4j
@Component
public class JwtProvider {

    private final Key key;
    private final long accessTokenExpireTime;
    private final long refreshTokenExpireTime;

    public JwtProvider(
            @Value("${spring.jwt.secret}") String secretKey,
            @Value("${spring.jwt.expiration}") long accessTokenExpireTime,
            @Value("${spring.jwt.refresh-token-expiration}") long refreshTokenExpireTime) {
        byte[] keyBytes = Base64.getDecoder().decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
        this.accessTokenExpireTime = accessTokenExpireTime;
        this.refreshTokenExpireTime = refreshTokenExpireTime;
    }

    // 1. 액세스 토큰 생성 (참고 코드 스타일)
    public String createAccessToken(String username, String role, String provider) {
        Date now = new Date();
        Claims claims = Jwts.claims().setSubject(username).setId(username); // 참고 코드: id에도 username 저장
        claims.put("roles", role);
        claims.put("provider", provider);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + accessTokenExpireTime))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    // 2. 리프레시 토큰 생성 (참고 코드 스타일)
    public String createRefreshToken(String username, String tokenId) {
        Date now = new Date();
        return Jwts.builder()
                .setSubject(username)
                .setId(tokenId)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + refreshTokenExpireTime))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    // 3. 토큰 정보를 쿠키에 저장 (참고 코드 스타일)
    public void addJwtToCookie(HttpServletResponse response, String token, String name, long maxAge) {
        Cookie cookie = new Cookie(name, token);
        cookie.setHttpOnly(true);
        // cookie.setSecure(true); // HTTPS 환경에서만 전송되게 (개발 중엔 주석)
        cookie.setPath("/");
        cookie.setMaxAge((int) maxAge / 1000); // 단위: 초
        response.addCookie(cookie);
    }

    // 4. 토큰 유효성 검증 (CustomException 사용)
    public boolean validateToken(String token) {
        try {
            parseClaims(token);
            return true;
        } catch (ExpiredJwtException e) {
            throw new CustomException(AuthErrorCode.JWT_TOKEN_EXPIRED);
        } catch (UnsupportedJwtException e) {
            throw new CustomException(AuthErrorCode.UNSUPPORTED_TOKEN);
        } catch (MalformedJwtException e) {
            throw new CustomException(AuthErrorCode.MALFORMED_JWT_TOKEN);
        } catch (io.jsonwebtoken.security.SignatureException e) {
            throw new CustomException(AuthErrorCode.INVALID_SIGNATURE);
        } catch (IllegalArgumentException e) {
            throw new CustomException(AuthErrorCode.ILLEGAL_ARGUMENT);
        }
    }

    // 5. 남은 만료 시간 가져오기
    public long getExpiration(String accessToken) {
        Claims claims = parseClaims(accessToken);
        Date expiration = claims.getExpiration();
        long now = System.currentTimeMillis();
        return expiration.getTime() - now;
    }

    public String extractSocialId(String token) {
        return parseClaims(token).getSubject();
    }

    public String extractTokenId(String token) {
        return parseClaims(token).getId();
    }

    // 내부 키 반환
    private Key getSigningKey() {
        return key;
    }

    // 토큰 파싱 (내부 메서드)
    private Claims parseClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public Authentication getAuthentication(String token) {
        Claims claims = parseClaims(token);
        String username = claims.getSubject();
        String role = claims.get("roles", String.class);

        List<SimpleGrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority(role));
        UserDetails principal = new User(username, "", authorities);

        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }
}
