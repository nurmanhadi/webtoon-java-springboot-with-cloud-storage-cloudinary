package com.nurman.webtoon.service;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.nurman.webtoon.entity.Comic;
import com.nurman.webtoon.model.ComicAddRequest;
import com.nurman.webtoon.model.ComicUpdateRequest;
import com.nurman.webtoon.model.PageResponse;
import com.nurman.webtoon.repository.ComicRepository;

import jakarta.transaction.Transactional;

@Service
public class ComicService {
    @Autowired
    private ComicRepository comicRepository;

    @Autowired
    private ValidationService validationService;

    @Autowired
    private ImageService imageService;

    @Transactional
    public void addComic(MultipartFile cover, ComicAddRequest request) {
        validationService.validate(request);
        imageService.validate(cover);
        String filename = imageService.changeImageFilename(cover);
        Comic comic = new Comic();
        comic.setCover(filename);
        comic.setTitle(request.getTitle());
        comic.setSynopsis(request.getSynopsis());
        comic.setAuthor(request.getAuthor());
        comic.setArtist(request.getArtist());
        comic.setType(request.getType().toUpperCase());
        comicRepository.save(comic);
    }

    @Transactional
    public void updateComic(String comicId, MultipartFile cover, ComicUpdateRequest request) {
        validationService.validate(request);
        var newId = Integer.parseInt(comicId);
        Comic comic = comicRepository.findById(newId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "comic not found"));

        if (Objects.nonNull(cover.getOriginalFilename())) {
            System.out.println("lewat sini");
            imageService.validate(cover);
            String filename = imageService.changeImageFilename(cover);
            comic.setCover(filename);
        }
        if (Objects.nonNull(request.getTitle())) {
            comic.setTitle(request.getTitle());
        }
        if (Objects.nonNull(request.getSynopsis())) {
            comic.setSynopsis(request.getSynopsis());
        }
        if (Objects.nonNull(request.getAuthor())) {
            comic.setAuthor(request.getAuthor());
        }
        if (Objects.nonNull(request.getArtist())) {
            comic.setArtist(request.getArtist());
        }
        if (Objects.nonNull(request.getType())) {
            comic.setType(request.getType().toUpperCase());
        }
        comicRepository.save(comic);
    }

    @Transactional
    public Comic getById(String comicId) {
        var newId = Integer.parseInt(comicId);
        Comic comic = comicRepository.findById(newId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "comic not found"));
        return comic;
    }

    @Transactional
    public PageResponse<Comic> getAll(String page, String size) {
        var newPage = Integer.parseInt(page) - 1;
        var newSize = Integer.parseInt(size);
        Pageable pagging = PageRequest.of(newPage, newSize, Sort.by("createdAt").descending());
        var comics = comicRepository.findAll(pagging);

        PageResponse<Comic> pageAble = new PageResponse<Comic>();
        pageAble.setContents(comics.getContent());
        pageAble.setPage(newPage + 1);
        pageAble.setSize(newSize);
        pageAble.setTotalPages(comics.getTotalPages());
        pageAble.setTotalEmelents(comics.getTotalElements());
        return pageAble;
    }

    @Transactional
    public void delete(String comicId) {
        var newId = Integer.parseInt(comicId);
        if (!comicRepository.existsById(newId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "comic not found");
        }
        comicRepository.deleteById(newId);
    }
}
