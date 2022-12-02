package de.davidkoehlmann.ecommerceapplicationbackend.security;

import de.davidkoehlmann.ecommerceapplicationbackend.account.AccountService;
import de.davidkoehlmann.ecommerceapplicationbackend.jwt.JwtConfig;
import de.davidkoehlmann.ecommerceapplicationbackend.jwt.JwtHelper;
import de.davidkoehlmann.ecommerceapplicationbackend.jwt.JwtTokenVerifierFilter;
import de.davidkoehlmann.ecommerceapplicationbackend.jwt.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import javax.crypto.SecretKey;
import java.util.List;


@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
@EnableMethodSecurity
public class WebSecurityConfig {

    private final AccountService accountService;
    private final PasswordEncoder passwordEncoder;
    private final SecretKey secretKey;
    private final JwtConfig jwtConfig;
    private final JwtHelper jwtHelper;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable();

        http
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilter(new JwtAuthenticationFilter(authenticationManager(), jwtConfig, jwtHelper))
                .addFilterAfter(new JwtTokenVerifierFilter(secretKey, jwtConfig), JwtAuthenticationFilter.class);

        http.formLogin().disable();

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        return new ProviderManager(List.of(daoAuthenticationProvider()));
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(accountService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);

        return daoAuthenticationProvider;
    }

}
