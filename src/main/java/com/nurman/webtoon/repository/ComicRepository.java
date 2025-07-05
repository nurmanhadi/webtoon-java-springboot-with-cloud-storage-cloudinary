package com.nurman.webtoon.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import com.nurman.webtoon.entity.Comic;

@Repository
public interface ComicRepository extends JpaRepository<Comic, Integer> {
    @Override
    @NonNull
    @EntityGraph(attributePaths = "chapters")
    List<Comic> findAll();

    @Override
    @NonNull
    @EntityGraph(attributePaths = {
            "comicCategories",
            "comicCategories.category"
    })
    Optional<Comic> findById(@NonNull Integer id);

    Page<Comic> findAllByTitleContainingIgnoreCase(String keyword, Pageable pageable);

    Page<Comic> findAllByType(String type, Pageable pageable);
}
