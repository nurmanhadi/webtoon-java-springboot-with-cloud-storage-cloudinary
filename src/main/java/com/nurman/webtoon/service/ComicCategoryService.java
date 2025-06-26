package com.nurman.webtoon.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.nurman.webtoon.entity.ComicCategory;
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
}
