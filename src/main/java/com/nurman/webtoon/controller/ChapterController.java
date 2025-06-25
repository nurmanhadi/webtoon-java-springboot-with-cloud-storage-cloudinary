package com.nurman.webtoon.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;

import com.nurman.webtoon.model.WebResponse;
import com.nurman.webtoon.model.chapter.ChapterRequest;
import com.nurman.webtoon.model.chapter.ChapterResponse;
import com.nurman.webtoon.service.ChapterService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@RestController
public class ChapterController {
    private final String publicPath = "api/public/comics/{comicId}/chapters";
    private final String adminPath = "api/admin/comics/{comicId}/chapters";
    @Autowired
    private ChapterService chapterService;

    @PostMapping(path = adminPath, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(code = HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN')")
    public WebResponse<String> addChapter(
            @PathVariable("comicId") String comicId,
            @RequestBody ChapterRequest request) {
        chapterService.add(comicId, request);
        return WebResponse.<String>builder().data("OK").build();
    }

    @PutMapping(path = adminPath
            + "/{chapterId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(code = HttpStatus.OK)
    @PreAuthorize("hasRole('ADMIN')")
    public WebResponse<String> updateChapter(
            @PathVariable("comicId") String comicId,
            @PathVariable("chapterId") String chapterId,
            @RequestBody ChapterRequest request) {
        chapterService.update(comicId, chapterId, request);
        return WebResponse.<String>builder().data("OK").build();
    }

    @GetMapping(path = publicPath
            + "/{chapterId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(code = HttpStatus.OK)
    public WebResponse<ChapterResponse> getChapterById(
            @PathVariable("comicId") String comicId,
            @PathVariable("chapterId") String chapterId) {
        var response = chapterService.getById(comicId, chapterId);
        return WebResponse.<ChapterResponse>builder().data(response).build();
    }

    @GetMapping(path = publicPath, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(code = HttpStatus.OK)
    public WebResponse<List<ChapterResponse>> getAllChapterByComicId(
            @PathVariable("comicId") String comicId) {
        var response = chapterService.getAllByComicId(comicId);
        return WebResponse.<List<ChapterResponse>>builder().data(response).build();
    }

    @DeleteMapping(path = adminPath
            + "/{chapterId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(code = HttpStatus.OK)
    @PreAuthorize("hasRole('ADMIN')")
    public WebResponse<String> deleteChapter(
            @PathVariable("comicId") String comicId,
            @PathVariable("chapterId") String chapterId) {
        chapterService.delete(comicId, chapterId);
        return WebResponse.<String>builder().data("OK").build();
    }
}
