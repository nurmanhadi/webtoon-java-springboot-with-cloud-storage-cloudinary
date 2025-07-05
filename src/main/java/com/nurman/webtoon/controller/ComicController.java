package com.nurman.webtoon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.nurman.webtoon.model.PageResponse;
import com.nurman.webtoon.model.WebResponse;
import com.nurman.webtoon.model.comic.ComicAddRequest;
import com.nurman.webtoon.model.comic.ComicResponse;
import com.nurman.webtoon.model.comic.ComicUpdateRequest;
import com.nurman.webtoon.service.ComicService;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
public class ComicController {
    private final String publicPath = "api/public/comics";
    private final String adminPath = "api/admin/comics";
    @Autowired
    private ComicService comicService;

    @PostMapping(path = adminPath, consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(code = HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN')")
    public WebResponse<String> addComic(
            @RequestParam("cover") MultipartFile cover,
            @RequestParam("title") String title,
            @RequestParam("synopsis") String synopsis,
            @RequestParam("author") String author,
            @RequestParam("artist") String artist,
            @RequestParam("type") String type) {

        var request = new ComicAddRequest();
        request.setTitle(title);
        request.setSynopsis(synopsis);
        request.setAuthor(author);
        request.setArtist(artist);
        request.setType(type);
        comicService.add(cover, request);
        return WebResponse.<String>builder().data("OK").build();
    }

    @PutMapping(path = adminPath
            + "/{comicId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(code = HttpStatus.OK)
    @PreAuthorize("hasRole('ADMIN')")
    public WebResponse<String> updateComic(
            @PathVariable(value = "comicId", required = false) String comicId,
            @RequestParam(value = "cover", required = false) MultipartFile cover,
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "synopsis", required = false) String synopsis,
            @RequestParam(value = "author", required = false) String author,
            @RequestParam(value = "artist", required = false) String artist,
            @RequestParam(value = "type", required = false) String type) {

        var request = new ComicUpdateRequest();
        request.setTitle(title);
        request.setSynopsis(synopsis);
        request.setAuthor(author);
        request.setArtist(artist);
        request.setType(type);
        comicService.update(comicId, cover, request);
        return WebResponse.<String>builder().data("OK").build();
    }

    @GetMapping(path = publicPath, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(code = HttpStatus.OK)
    public WebResponse<PageResponse<ComicResponse>> getAllComic(
            @RequestParam String page,
            @RequestParam String size) {
        var response = comicService.getAll(page, size);
        return WebResponse.<PageResponse<ComicResponse>>builder().data(response).build();
    }

    @GetMapping(path = publicPath + "/{comicId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(code = HttpStatus.OK)
    public WebResponse<ComicResponse> getComicById(@PathVariable String comicId) {
        var response = comicService.getById(comicId);
        return WebResponse.<ComicResponse>builder().data(response).build();
    }

    @DeleteMapping(path = adminPath + "/{comicId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(code = HttpStatus.OK)
    @PreAuthorize("hasRole('ADMIN')")
    public WebResponse<String> delete(@PathVariable String comicId) {
        comicService.delete(comicId);
        return WebResponse.<String>builder().data("OK").build();
    }

    @GetMapping(path = publicPath + "/search", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(code = HttpStatus.OK)
    public WebResponse<PageResponse<ComicResponse>> searchComic(
            @RequestParam("keyword") String keyword,
            @RequestParam("page") String page,
            @RequestParam("size") String size) {
        var response = comicService.search(keyword, page, size);
        return WebResponse.<PageResponse<ComicResponse>>builder().data(response).build();
    }

    @GetMapping(path = publicPath + "/type", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(code = HttpStatus.OK)
    public WebResponse<PageResponse<ComicResponse>> getAllComicByType(
            @RequestParam("type") String type,
            @RequestParam("page") String page,
            @RequestParam("size") String size) {
        var response = comicService.getAllByType(type, page, size);
        return WebResponse.<PageResponse<ComicResponse>>builder().data(response).build();
    }
}
