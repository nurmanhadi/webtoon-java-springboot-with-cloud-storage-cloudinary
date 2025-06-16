package com.nurman.webtoon.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ImageService {

    public void validate(MultipartFile image) {
        var type = image.getContentType();
        if (type == null || !type.equalsIgnoreCase("image/jpeg")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "cover most be type .jpg or JPG");
        }
    }
}
