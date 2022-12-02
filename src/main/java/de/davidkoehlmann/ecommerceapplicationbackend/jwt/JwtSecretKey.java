package de.davidkoehlmann.ecommerceapplicationbackend.jwt;

import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.crypto.SecretKey;

@Configuration
@RequiredArgsConstructor
public class JwtSecretKey {

    private final JwtConfig jwtConfig;

    @Bean
    SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(jwtConfig.getSecretKey().getBytes());
    }
}
