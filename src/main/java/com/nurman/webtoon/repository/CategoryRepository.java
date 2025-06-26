package com.nurman.webtoon.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import com.nurman.webtoon.entity.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    @Override
    @NonNull
    @EntityGraph(attributePaths = {
            "comicCategories",
            "comicCategories.comic"
    })
    Optional<Category> findById(@NonNull Integer id);
}
