package de.davidkoehlmann.ecommerceapplicationbackend.jwt;

import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;

import javax.crypto.SecretKey;
import java.sql.Date;
import java.time.LocalDate;

@Configuration
@RequiredArgsConstructor
public class JwtHelper {
    private final SecretKey secretKey;
    private final JwtConfig jwtConfig;

    public String generateAccessToken(Authentication authResult) {
        return Jwts.builder()
                .setSubject(authResult.getName())
                .claim("authorities", authResult.getAuthorities())
                .setIssuedAt(Date.valueOf(LocalDate.now()))
                .setExpiration(Date.valueOf(LocalDate.now().plusDays(jwtConfig.getAccessTokenExpirationAfterDays())))
                .signWith(secretKey)
                .compact();
    }

    public String generateRefreshToken(Authentication authResult) {
        return Jwts.builder()
                .setSubject(authResult.getName())
                .setIssuedAt(Date.valueOf(LocalDate.now()))
                .setExpiration(Date.valueOf(LocalDate.now().plusDays(jwtConfig.getRefreshTokenExpirationAfterDays())))
                .signWith(secretKey)
                .compact();
    }
}
