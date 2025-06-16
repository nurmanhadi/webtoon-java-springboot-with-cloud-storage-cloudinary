package com.nurman.webtoon.service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.nurman.webtoon.model.AuthLoginRequest;
import com.nurman.webtoon.model.TokenResponse;
import com.nurman.webtoon.repository.UserRepository;
import com.nurman.webtoon.security.BCrypt;

import jakarta.transaction.Transactional;

@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ValidationService validationService;

    @Transactional
    public TokenResponse login(AuthLoginRequest request) {
        validationService.validate(request);
        var user = userRepository.findById(request.getUsername())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "username or password wrong"));
        var checkPw = BCrypt.checkpw(request.getPassword(), user.getPassword());
        if (checkPw != true) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "username or password wrong");
        }
        HashMap<String, String> claim = new HashMap<>();
        claim.put("username", user.getUsername());
        claim.put("role", user.getRole());
        var exp = Date.from(Instant.now().plus(7, ChronoUnit.DAYS));
        String token = JWT.create().withPayload(claim).withIssuer("auth0").withExpiresAt(exp)
                .sign(Algorithm.HMAC256("nurman"));

        return TokenResponse.builder().token(token).build();
    }
}
