package com.nurman.webtoon.service;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

@Service
public class CloudinaryService {
    @Autowired
    private Cloudinary cloudinary;

    public String uploadImage(MultipartFile cover, String filename) {
        try {
            Map<?, ?> uploadResult = cloudinary.uploader().upload(cover.getBytes(), ObjectUtils.asMap(
                    "public_id", filename,
                    "overwrite", true,
                    "resource_type", "image"));
            return uploadResult.get("secure_url").toString();
        } catch (IOException e) {
            throw new RuntimeException("upload cover failed: " + e.getMessage());
        }
    }

    public void deleteImage(String filename) {
        try {
            cloudinary.uploader().destroy(filename, ObjectUtils.emptyMap());
        } catch (IOException e) {
            throw new RuntimeException("upload cover failed: " + e.getMessage());
        }
    }
}
