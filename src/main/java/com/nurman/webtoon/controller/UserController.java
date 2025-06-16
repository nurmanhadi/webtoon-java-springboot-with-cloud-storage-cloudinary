package com.nurman.webtoon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RestController;

import com.nurman.webtoon.model.UserRegisterRequest;
import com.nurman.webtoon.model.WebResponse;
import com.nurman.webtoon.service.UserService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping(path = "api/users", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(code = HttpStatus.CREATED)
    public WebResponse<String> register(@RequestBody UserRegisterRequest request) {
        userService.register(request);
        return WebResponse.<String>builder().data("OK").build();
    }

    @PostMapping(path = "api/admins", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(code = HttpStatus.CREATED)
    public WebResponse<String> addAdmin(@RequestBody UserRegisterRequest request) {
        userService.register(request);
        return WebResponse.<String>builder().data("OK").build();
    }
}
