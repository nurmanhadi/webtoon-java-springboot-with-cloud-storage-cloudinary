package com.nurman.webtoon.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nurman.webtoon.entity.Content;

@Repository
public interface ContentRepository extends JpaRepository<Content, Long> {
    List<Content> findAllByChapterId(Long chapterId);
}
