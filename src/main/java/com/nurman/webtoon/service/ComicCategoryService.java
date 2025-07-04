package com.nurman.webtoon.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.nurman.webtoon.entity.ComicCategory;
import com.nurman.webtoon.model.PageResponse;
import com.nurman.webtoon.model.comic.ComicResponse;
import com.nurman.webtoon.model.comicCategory.ComicCategoryRequest;
import com.nurman.webtoon.repository.CategoryRepository;
import com.nurman.webtoon.repository.ComicCategoryRepository;
import com.nurman.webtoon.repository.ComicRepository;

import jakarta.transaction.Transactional;

@Service
public class ComicCategoryService {
    @Autowired
    private ComicCategoryRepository comicCategoryRepository;
    @Autowired
    private ValidationService validationService;
    @Autowired
    private ComicRepository comicRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    @Transactional
    public void add(ComicCategoryRequest request) {
        validationService.validate(request);
        var comic = comicRepository.findById(request.getComicId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "comic not found"));
        var category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "category not found"));
        ComicCategory comicCategory = new ComicCategory();
        comicCategory.setComic(comic);
        comicCategory.setCategory(category);
        comicCategoryRepository.save(comicCategory);
    }

    @Transactional
    public void delete(String comicCategoryId) {
        Long newcomicCategoryId = (long) Integer.parseInt(comicCategoryId);
        if (!comicCategoryRepository.existsById(newcomicCategoryId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "comic category not found");
        }
        comicCategoryRepository.deleteById(newcomicCategoryId);
    }

    @Transactional
    public PageResponse<ComicResponse> getAllByCategoryId(String categoryId, String page, String size) {
        Integer newCategoryId = Integer.parseInt(categoryId);
        Integer newPage = Integer.parseInt(page) - 1;
        Integer newSize = Integer.parseInt(size);
        Pageable pagging = PageRequest.of(newPage, newSize);
        var comicCategory = comicCategoryRepository.findAllByCategoryId(newCategoryId, pagging);

        List<ComicResponse> comicResponse = comicCategory.stream()
                .sorted((c1, c2) -> c2.getComic().getCreatedAt().compareTo(c1.getComic().getCreatedAt()))
                .map(cc -> ComicResponse.builder()
                        .id(cc.getComic().getId())
                        .cover(cc.getComic().getCover())
                        .title(cc.getComic().getTitle())
                        .synopsis(cc.getComic().getSynopsis())
                        .author(cc.getComic().getAuthor())
                        .artist(cc.getComic().getArtist())
                        .type(cc.getComic().getType())
                        .url(cc.getComic().getUrl())
                        .createdAt(cc.getComic().getCreatedAt())
                        .build())
                .toList();
        return PageResponse.<ComicResponse>builder()
                .contents(comicResponse)
                .page(newPage + 1)
                .size(newSize)
                .totalPages(comicCategory.getTotalPages())
                .totalEmelents(comicCategory.getTotalElements()).build();

    }
}
