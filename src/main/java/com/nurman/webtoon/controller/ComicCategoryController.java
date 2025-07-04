package com.nurman.webtoon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;

import com.nurman.webtoon.model.PageResponse;
import com.nurman.webtoon.model.WebResponse;
import com.nurman.webtoon.model.comic.ComicResponse;
import com.nurman.webtoon.model.comicCategory.ComicCategoryRequest;
import com.nurman.webtoon.service.ComicCategoryService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
public class ComicCategoryController {
    private final String adminPath = "api/admin/comic-categories";
    private final String publicPath = "api/public/comic-categories/categories";
    @Autowired
    private ComicCategoryService comicCategoryService;

    @PostMapping(path = adminPath, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(code = HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN')")
    public WebResponse<String> addComicCategory(@RequestBody ComicCategoryRequest request) {
        comicCategoryService.add(request);
        return WebResponse.<String>builder().data("OK").build();
    }

    @DeleteMapping(path = adminPath
            + "/{comicCategoryId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(code = HttpStatus.OK)
    @PreAuthorize("hasRole('ADMIN')")
    public WebResponse<String> deleteComicCategory(@PathVariable String comicCategoryId) {
        comicCategoryService.delete(comicCategoryId);
        return WebResponse.<String>builder().data("OK").build();
    }

    @GetMapping(path = publicPath + "/{categoryId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(code = HttpStatus.OK)
    public WebResponse<PageResponse<ComicResponse>> getMethodName(
            @PathVariable String categoryId,
            @RequestParam("page") String page,
            @RequestParam("size") String size) {
        var response = comicCategoryService.getAllByCategoryId(categoryId, page, size);
        return WebResponse.<PageResponse<ComicResponse>>builder().data(response).build();
    }

}
