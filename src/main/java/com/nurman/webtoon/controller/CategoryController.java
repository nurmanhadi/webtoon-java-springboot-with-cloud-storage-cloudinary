package com.nurman.webtoon.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;

import com.nurman.webtoon.model.WebResponse;
import com.nurman.webtoon.model.category.CategoryRequest;
import com.nurman.webtoon.model.category.CategoryResponse;
import com.nurman.webtoon.service.CategoryService;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@RestController
public class CategoryController {
    private final String publicPath = "api/public/categories";
    private final String adminPath = "api/admin/categories";

    @Autowired
    private CategoryService categoryService;

    @PostMapping(path = adminPath, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(code = HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN')")
    public WebResponse<String> addCategory(@RequestBody CategoryRequest request) {
        categoryService.add(request);
        return WebResponse.<String>builder().data("OK").build();
    }

    @PutMapping(path = adminPath
            + "/{categoryId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(code = HttpStatus.OK)
    @PreAuthorize("hasRole('ADMIN')")
    public WebResponse<String> addCategory(@PathVariable("categoryId") String categoryId,
            @RequestBody CategoryRequest request) {
        categoryService.update(categoryId, request);
        return WebResponse.<String>builder().data("OK").build();
    }

    @PutMapping(path = publicPath, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(code = HttpStatus.OK)
    public WebResponse<List<CategoryResponse>> getAllCategories() {
        var response = categoryService.getAll();
        return WebResponse.<List<CategoryResponse>>builder().data(response).build();
    }
}
