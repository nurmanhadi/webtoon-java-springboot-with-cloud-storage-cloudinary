package com.nurman.webtoon.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.nurman.webtoon.entity.User;
import com.nurman.webtoon.helper.UserRole;
import com.nurman.webtoon.model.user.UserRegisterRequest;
import com.nurman.webtoon.repository.UserRepository;
import com.nurman.webtoon.security.BCrypt;

import jakarta.transaction.Transactional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ValidationService validationService;

    @Transactional
    public void register(UserRegisterRequest request) {
        validationService.validate(request);
        if (userRepository.existsById(request.getUsername())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "username already exists");
        }
        var hashPw = BCrypt.hashpw(request.getPassword(), BCrypt.gensalt());
        var user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(hashPw);
        user.setRole(UserRole.USER.toString());
        userRepository.save(user);
    }

    @Transactional
    public void addAdmin(UserRegisterRequest request) {
        validationService.validate(request);
        if (userRepository.existsById(request.getUsername())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "username already exists");
        }
        var hashPw = BCrypt.hashpw(request.getPassword(), BCrypt.gensalt());
        var user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(hashPw);
        user.setRole(UserRole.ADMIN.toString());
        userRepository.save(user);
    }
}
