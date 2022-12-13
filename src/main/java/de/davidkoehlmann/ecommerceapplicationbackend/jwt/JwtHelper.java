package de.davidkoehlmann.ecommerceapplicationbackend.jwt;

import de.davidkoehlmann.ecommerceapplicationbackend.account.Account;
import de.davidkoehlmann.ecommerceapplicationbackend.util.Utils;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;

import javax.crypto.SecretKey;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Configuration
@RequiredArgsConstructor
public class JwtHelper {
    private final SecretKey secretKey;
    private final JwtConfig jwtConfig;

    public String generateAccessToken(Authentication authResult) {
        return Jwts.builder()
                .setSubject(authResult.getName())
                .claim("authorities", authResult.getAuthorities())
                .setIssuedAt(Timestamp.valueOf(LocalDateTime.now()))
                .setExpiration(Utils.getCurrentDatePlusMinutes(30))
                .signWith(secretKey)
                .compact();
    }

    public String generateRefreshToken(Authentication authResult) {
        return Jwts.builder()
                .setSubject(authResult.getName())
                .setIssuedAt(Timestamp.valueOf(LocalDateTime.now()))
                .setExpiration(Utils.getCurrentDatePlusDays(jwtConfig.getRefreshTokenExpirationAfterDays()))
                .signWith(secretKey)
                .compact();
    }

    public String generateAccessTokenWithAccount(Account account) {
        return Jwts.builder()
                .setSubject(account.getUsername())
                .claim("authorities", account.getAuthorities())
                .setIssuedAt(Timestamp.valueOf(LocalDateTime.now()))
                .setExpiration(Utils.getCurrentDatePlusMinutes(30))
                .signWith(secretKey)
                .compact();
    }
}
