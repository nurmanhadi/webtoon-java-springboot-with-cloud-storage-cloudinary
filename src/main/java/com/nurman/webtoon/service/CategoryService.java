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
                                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                                                "comic not found"));
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
}
