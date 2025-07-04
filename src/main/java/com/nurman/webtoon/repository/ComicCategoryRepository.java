package com.nurman.webtoon.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nurman.webtoon.entity.ComicCategory;

@Repository
public interface ComicCategoryRepository extends JpaRepository<ComicCategory, Long> {

    @EntityGraph(attributePaths = "category")
    List<ComicCategory> findAllByComicId(Integer comicId);

    @EntityGraph(attributePaths = "comic")
    Page<ComicCategory> findAllByCategoryId(Integer categoryId, Pageable pageable);
}
