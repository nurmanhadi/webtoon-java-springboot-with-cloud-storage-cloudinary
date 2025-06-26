package com.nurman.webtoon.repository;

import java.util.List;

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
}
