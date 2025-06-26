package com.nurman.webtoon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RestController;

import com.nurman.webtoon.model.TokenResponse;
import com.nurman.webtoon.model.WebResponse;
import com.nurman.webtoon.model.auth.AuthLoginRequest;
import com.nurman.webtoon.model.user.UserRegisterRequest;
import com.nurman.webtoon.service.AuthService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@RestController
public class AuthController {
    private final String authPath = "api/auth";
    @Autowired
    private AuthService authService;

    @PostMapping(path = authPath
            + "/register", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(code = HttpStatus.CREATED)
    public WebResponse<String> registerUser(@RequestBody UserRegisterRequest request) {
        authService.register(request);
        return WebResponse.<String>builder().data("OK").build();
    }

    @PostMapping(path = authPath
            + "/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(code = HttpStatus.OK)
    public WebResponse<TokenResponse> login(@RequestBody AuthLoginRequest request) {
        var token = authService.login(request);
        return WebResponse.<TokenResponse>builder().data(token).build();
    }
}
