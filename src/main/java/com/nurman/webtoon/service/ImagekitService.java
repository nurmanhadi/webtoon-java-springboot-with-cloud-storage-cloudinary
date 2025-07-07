package com.nurman.webtoon.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import io.imagekit.sdk.ImageKit;
import io.imagekit.sdk.models.FileCreateRequest;
import io.imagekit.sdk.models.results.Result;

@Service
public class ImagekitService {
    @Autowired
    private ImageKit imageKit;

    public String upload(MultipartFile image, String filename) {
        try {
            FileCreateRequest request = new FileCreateRequest(image.getBytes(), filename);
            request.setUseUniqueFileName(false);
            request.setOverwriteFile(true);

            Result upload = imageKit.upload(request);
            String baseUrl = upload.getUrl();
            String fieldId = upload.getFileId();
            return baseUrl + "?v=" + fieldId;
        } catch (Exception e) {
            throw new RuntimeException("Upload failed: " + e.getMessage());
        }
    }
}
