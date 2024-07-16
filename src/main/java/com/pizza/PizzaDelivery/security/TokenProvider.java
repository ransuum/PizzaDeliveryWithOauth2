package com.pizza.PizzaDelivery.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.pizza.PizzaDelivery.entity.Users;
import jakarta.servlet.http.Cookie;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

@Service
public class TokenProvider {

    @Value("${frontend.domain}")
    private String frontEndDomain;

    @Value("${security.jwt.token.secret-key:secret-key}")
    private String JWT_SECRET;


    public String generateAccessToken(Users users) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(JWT_SECRET);
            return JWT.create()
                    .withSubject(users.getUsername())
                    .withClaim("userId", users.getId().toString())
                    .withExpiresAt(Date.from(genAccessExpirationDate()))
                    .sign(algorithm);
        } catch (JWTCreationException exception) {
            throw new JWTCreationException("Error while generating token", exception);
        }
    }

    public String validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(JWT_SECRET);
            return JWT.require(algorithm)
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException exception) {
            throw new JWTVerificationException("Error while validating token", exception);
        }
    }

    public Instant genAccessExpirationDate() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.UTC);
    }

    public int getAccessCookieExpirationInSeconds(){
        return (int) (genAccessExpirationDate().getEpochSecond() - Instant.now().getEpochSecond());
    }

    public Cookie generateAuthorizationCookie(Users users){
        Cookie jwtCookie = new Cookie("Authorization", generateAccessToken(users));
        jwtCookie.setHttpOnly(true);
        jwtCookie.setSecure(true);
        jwtCookie.setDomain(frontEndDomain);
        jwtCookie.setMaxAge(getAccessCookieExpirationInSeconds());
        jwtCookie.setPath("/");
        return jwtCookie;
    }
}
