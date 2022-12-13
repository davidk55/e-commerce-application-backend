package de.davidkoehlmann.ecommerceapplicationbackend.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.davidkoehlmann.ecommerceapplicationbackend.util.Utils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.Optional;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    private final JwtConfig jwtConfig;
    private final JwtHelper jwtHelper;


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            UsernameAndPasswordAuthenticationRequest authenticationRequest = new ObjectMapper()
                    .readValue(request.getInputStream(), UsernameAndPasswordAuthenticationRequest.class);

            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    authenticationRequest.getUsername(),
                    authenticationRequest.getPassword()
            );
            return authenticationManager.authenticate(authentication);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        JSONObject res = new JSONObject();

        // Access Token
        String accessToken = jwtHelper.generateAccessToken(authResult);
        res.put("AccessToken", accessToken);


        // Role
        Optional<? extends GrantedAuthority> roleOpt = authResult.getAuthorities().stream()
                .filter(auth -> auth.toString()
                        .startsWith("ROLE_"))
                .findFirst();
        if (roleOpt.isEmpty()) throw new IllegalArgumentException("No role found");
        String role = roleOpt.get().toString();
        res.put("Role", role);

        // Misc
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        response.getWriter().write(res.toString());

        // Refresh Token
        String refreshToken = jwtHelper.generateRefreshToken(authResult);
        Cookie refreshTokenCookie = new Cookie("jwt", refreshToken);
        refreshTokenCookie.setHttpOnly(true);
//        refreshTokenCookie.setSecure(true);
        refreshTokenCookie.setMaxAge(Utils.daysToSeconds(jwtConfig.getRefreshTokenExpirationAfterDays()));
        response.addCookie(refreshTokenCookie);
    }
}
