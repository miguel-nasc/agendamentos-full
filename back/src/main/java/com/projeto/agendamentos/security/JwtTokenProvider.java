package com.projeto.agendamentos.security;

import com.projeto.agendamentos.dtos.security.TokenDTO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;

@Service
public class JwtTokenProvider {

    @Value("${security.jwt.token.secret-key}")
    private String secretKey;

    private SecretKey key;

    @Value("${security.jwt.token.expire-length}")
    private long validityInMilliseconds;

    private final UserDetailsService userDetailsService;

    public JwtTokenProvider(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @PostConstruct
    protected void init() {
        key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    public TokenDTO createAccessToken(String username, List<String> roles) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMilliseconds);
        String accessToken = getAccessToken(username, roles, now, validity);
        String refreshToken = getRefreshToken(username, roles, now);
        return createTokenDTO(username, true, now, validity, accessToken, refreshToken);
    }

    public TokenDTO refreshToken(String refreshToken) {
        var token = "";
        if (refreshTokenContainsBearer(refreshToken)) {
            token = refreshToken.substring("Bearer ".length());
        }
        JwtParser verifier = Jwts.parser().verifyWith(key).build();
        Claims decodedJWT = verifier.parseSignedClaims(token).getPayload();
        String username = decodedJWT.getSubject();
        List<String> roles = decodedJWT.get("roles", List.class);
        return createAccessToken(username, roles);
    }


    private String getAccessToken(String username, List<String> roles, Date now, Date validity) {
        String issuerUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
        return Jwts.builder()
                .claim("roles", roles)
                .expiration(validity)
                .issuedAt(now)
                .subject(username)
                .issuer(issuerUrl)
                .signWith(key, Jwts.SIG.HS256)
                .compact();
    }

    private String getRefreshToken(String username, List<String> roles, Date now) {
        Date refreshTokenValidity = new Date(now.getTime() + validityInMilliseconds);
        return Jwts.builder()
                .claim("roles", roles)
                .expiration(refreshTokenValidity)
                .issuedAt(now)
                .subject(username)
                .signWith(key, Jwts.SIG.HS256)
                .compact();
    }

    public Authentication getAuthentication(String token) {
        Claims claims = decodedToken(token);
        UserDetails userDetails = this.userDetailsService
                .loadUserByUsername(claims.getSubject());
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    private Claims decodedToken(String token) {
        try{
            JwtParser verifier = Jwts.parser()
                    .verifyWith(key)
                    .build();
            return verifier.parseSignedClaims(token).getPayload();
        } catch (JwtException e) {
            throw new JwtException("Expired or Invalid token!");
        }
    }

    private TokenDTO createTokenDTO(String username, Boolean auth, Date now, Date validity, String accessToken, String refreshToken) {
        return new TokenDTO(username, auth, now, validity, accessToken, refreshToken);
    }

    private static boolean refreshTokenContainsBearer(String refreshToken) {
        return StringUtils.isNotBlank(refreshToken) && refreshToken.startsWith("Bearer ");
    }

    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (!StringUtils.isEmpty(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring("Bearer ".length());
        }
        return null;
    }

    public boolean validateToken(String token) {
        try {
            Claims claim  = decodedToken(token);
            return !claim.getExpiration().before(new Date());
        } catch (JwtException e) {
            throw new JwtException("Expired or Invalid Token!");
        }
    }

}