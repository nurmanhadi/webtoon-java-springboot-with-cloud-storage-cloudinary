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
import com.nurman.webtoon.model.TokenResponse;
import com.nurman.webtoon.model.auth.AuthLoginRequest;
import com.nurman.webtoon.repository.UserRepository;
import com.nurman.webtoon.security.BCrypt;

import jakarta.transaction.Transactional;

@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ValidationService validationService;
    @Autowired
    private JwtService jwtService;

    @Transactional
    public TokenResponse login(AuthLoginRequest request) {
        validationService.validate(request);
        var user = userRepository.findById(request.getUsername())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "username or password wrong"));
        var checkPw = BCrypt.checkpw(request.getPassword(), user.getPassword());
        if (checkPw != true) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "username or password wrong");
        }
        String token = jwtService.create(user.getUsername(), user.getRole());

        return TokenResponse.builder().token(token).build();
    }
}
