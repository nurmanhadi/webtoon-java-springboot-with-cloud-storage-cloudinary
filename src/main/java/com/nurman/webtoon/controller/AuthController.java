package com.nurman.webtoon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RestController;

import com.nurman.webtoon.model.AuthLoginRequest;
import com.nurman.webtoon.model.TokenResponse;
import com.nurman.webtoon.model.WebResponse;
import com.nurman.webtoon.service.AuthService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@RestController
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping(path = "api/auth/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(code = HttpStatus.OK)
    public WebResponse<TokenResponse> login(@RequestBody AuthLoginRequest request) {
        var token = authService.login(request);
        return WebResponse.<TokenResponse>builder().data(token).build();
    }

}
