package com.nurman.webtoon.service;

import java.time.Instant;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

@Service
public class JwtService {
    @Autowired
    private Environment env;

    public String create(String username, String role) {
        return JWT.create()
                .withClaim("username", username)
                .withClaim("role", role)
                .withIssuer("auth0")
                .withExpiresAt(Date.from(Instant.now().plus(7, ChronoUnit.DAYS)))
                .sign(Algorithm.HMAC256(env.getProperty("jwt.secret")));
    }

    public DecodedJWT verify(String token) {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(env.getProperty("jwt.secret")))
                .withIssuer("auth0")
                .build();
        return verifier.verify(token);
    }
}
