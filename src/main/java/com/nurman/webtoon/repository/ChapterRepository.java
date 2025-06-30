package com.nurman.webtoon.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nurman.webtoon.entity.Chapter;

@Repository
public interface ChapterRepository extends JpaRepository<Chapter, Long> {
    List<Chapter> findAllByComicId(Integer comicId);

    Optional<Chapter> findByComic_IdAndNumber(Integer comicId, Integer number);
}
