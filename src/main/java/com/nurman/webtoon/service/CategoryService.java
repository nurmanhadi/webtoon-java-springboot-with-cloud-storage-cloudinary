package com.nurman.webtoon.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.nurman.webtoon.entity.Category;
import com.nurman.webtoon.model.category.CategoryRequest;
import com.nurman.webtoon.model.category.CategoryResponse;
import com.nurman.webtoon.model.comic.ComicResponse;
import com.nurman.webtoon.model.comicCategory.ComicCategoryResponse;
import com.nurman.webtoon.repository.CategoryRepository;

import jakarta.transaction.Transactional;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ValidationService validationService;

    @Transactional
    public void add(CategoryRequest request) {
        validationService.validate(request);
        Category category = new Category();
        category.setName(request.getName());
        categoryRepository.save(category);
    }

    @Transactional
    public void update(String categoryId, CategoryRequest request) {
        Integer newCategoryId = Integer.parseInt(categoryId);
        validationService.validate(request);
        var category = categoryRepository.findById(newCategoryId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "comic not found"));
        category.setName(request.getName());
        categoryRepository.save(category);
    }

    @Transactional
    public List<CategoryResponse> getAll() {
        var categories = categoryRepository.findAll();
        List<CategoryResponse> response = categories.stream().map(c -> CategoryResponse.builder()
                .id(c.getId())
                .name(c.getName())
                .build()).toList();
        return response;
    }

    @Transactional
    public CategoryResponse getById(String categoryId) {
        Integer newCategoryId = Integer.parseInt(categoryId);
        var category = categoryRepository.findById(newCategoryId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "category not found"));
        return CategoryResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .comicCategories(category.getComicCategories().stream().map(cc -> ComicCategoryResponse
                        .builder()
                        .id(cc.getId())
                        .comic(ComicResponse.builder()
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
                        .build()).toList())
                .build();
    }
}
