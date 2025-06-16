package com.nurman.webtoon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.nurman.webtoon.entity.Comic;
import com.nurman.webtoon.model.ComicAddRequest;
import com.nurman.webtoon.model.ComicUpdateRequest;
import com.nurman.webtoon.model.PageResponse;
import com.nurman.webtoon.model.WebResponse;
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
    private final String pathPublick = "api/comics";
    private final String pathSecure = "api/secure/comics";
    @Autowired
    private ComicService comicService;

    @PostMapping(path = pathSecure, consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(code = HttpStatus.CREATED)
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
        comicService.addComic(cover, request);
        return WebResponse.<String>builder().data("OK").build();
    }

    @PutMapping(path = pathSecure
            + "/{comicId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(code = HttpStatus.OK)
    public WebResponse<String> updateComic(
            @PathVariable("comicId") String comicId,
            @RequestParam(value = "cover", required = false) MultipartFile cover,
            @RequestParam("title") String title,
            @RequestParam("synopsis") String synopsis,
            @RequestParam("author") String author,
            @RequestParam("artist") String artist,
            @RequestParam("type") String type) {

        var request = new ComicUpdateRequest();
        request.setTitle(title);
        request.setSynopsis(synopsis);
        request.setAuthor(author);
        request.setArtist(artist);
        request.setType(type);
        comicService.updateComic(comicId, cover, request);
        return WebResponse.<String>builder().data("OK").build();
    }

    @GetMapping(path = pathPublick, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(code = HttpStatus.OK)
    public WebResponse<PageResponse<Comic>> getMethodName(@RequestParam String page, @RequestParam String size) {
        var comics = comicService.getAll(page, size);
        return WebResponse.<PageResponse<Comic>>builder().data(comics).build();
    }

    @GetMapping(path = pathPublick + "/{comicId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(code = HttpStatus.OK)
    public WebResponse<Comic> getById(@PathVariable String comicId) {
        var comic = comicService.getById(comicId);
        return WebResponse.<Comic>builder().data(comic).build();
    }

    @DeleteMapping(path = pathSecure + "/{comicId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(code = HttpStatus.OK)
    public WebResponse<String> delete(@PathVariable String comicId) {
        comicService.delete(comicId);
        return WebResponse.<String>builder().data("OK").build();
    }
}
