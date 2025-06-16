package com.nurman.webtoon.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nurman.webtoon.entity.Comic;

@Repository
public interface ComicRepository extends JpaRepository<Comic, Integer> {

}
