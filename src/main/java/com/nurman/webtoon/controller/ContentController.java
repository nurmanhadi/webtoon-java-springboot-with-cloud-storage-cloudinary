package com.nurman.webtoon.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.nurman.webtoon.model.WebResponse;
import com.nurman.webtoon.model.content.ContentResponse;
import com.nurman.webtoon.service.ContentService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

@RestController
public class ContentController {
    private final String publicPath = "api/public/comics/{comicId}/chapters/{chapterNumber}/contents";
    private final String adminPath = "api/admin/comics/{comicId}/chapters/{chapterId}/contents";
    @Autowired
    private ContentService contentService;

    @PostMapping(path = adminPath, consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(code = HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN')")
    public WebResponse<String> addContent(
            @PathVariable("comicId") String comicId,
            @PathVariable("chapterId") String chapterId,
            @RequestParam("content") MultipartFile content) {
        contentService.add(comicId, chapterId, content);
        return WebResponse.<String>builder().data("OK").build();
    }

    @PutMapping(path = adminPath
            + "/{contentId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(code = HttpStatus.OK)
    @PreAuthorize("hasRole('ADMIN')")
    public WebResponse<String> updateContent(
            @PathVariable("comicId") String comicId,
            @PathVariable("chapterId") String chapterId,
            @PathVariable("contentId") String contentId,
            @RequestParam("content") MultipartFile content) {
        contentService.update(comicId, chapterId, contentId, content);
        return WebResponse.<String>builder().data("OK").build();
    }

    @GetMapping(path = publicPath, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(code = HttpStatus.OK)
    public WebResponse<List<ContentResponse>> getAllContentByChapterId(
            @PathVariable("comicId") String comicId,
            @PathVariable("chapterNumber") String chapterNumber) {
        var response = contentService.getAllByChapterNumber(comicId, chapterNumber);
        return WebResponse.<List<ContentResponse>>builder().data(response).build();
    }

    @DeleteMapping(path = adminPath + "/{contentId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(code = HttpStatus.OK)
    @PreAuthorize("hasRole('ADMIN')")
    public WebResponse<String> deleteContentById(
            @PathVariable("comicId") String comicId,
            @PathVariable("chapterId") String chapterId,
            @PathVariable("contentId") String contentId) {
        contentService.delete(comicId, chapterId, contentId);
        return WebResponse.<String>builder().data("OK").build();
    }
}
