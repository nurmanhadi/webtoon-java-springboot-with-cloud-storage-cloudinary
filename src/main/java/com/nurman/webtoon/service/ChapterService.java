package com.nurman.webtoon.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.nurman.webtoon.entity.Chapter;
import com.nurman.webtoon.model.chapter.ChapterRequest;
import com.nurman.webtoon.model.chapter.ChapterResponse;
import com.nurman.webtoon.repository.ChapterRepository;
import com.nurman.webtoon.repository.ComicRepository;

import jakarta.transaction.Transactional;

@Service
public class ChapterService {
    @Autowired
    private ChapterRepository chapterRepository;

    @Autowired
    private ComicRepository comicRepository;

    @Autowired
    private ValidationService validationService;

    @Transactional
    public void add(String comicId, ChapterRequest request) {
        validationService.validate(request);
        Integer newComicId = Integer.parseInt(comicId);
        var comic = comicRepository.findById(newComicId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "comic not found"));
        Chapter chapter = new Chapter();
        chapter.setNumber(request.getNumber());
        chapter.setComic(comic);

        chapterRepository.save(chapter);
    }

    @Transactional
    public void update(String comicId, String chapterId, ChapterRequest request) {
        validationService.validate(request);
        Integer newComicId = Integer.parseInt(comicId);
        Long newChapterId = (long) Integer.parseInt(chapterId);
        if (!comicRepository.existsById(newComicId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "comic not found");
        }
        var chapter = chapterRepository.findById(newChapterId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "chapter not found"));
        chapter.setNumber(request.getNumber());
        chapterRepository.save(chapter);
    }

    @Transactional
    public ChapterResponse getById(String comicId, String chapterId) {
        Integer newComicId = Integer.parseInt(comicId);
        Long newChapterId = (long) Integer.parseInt(chapterId);

        if (!comicRepository.existsById(newComicId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "comic not found");
        }
        var chapter = chapterRepository.findById(newChapterId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "chapter not found"));
        return ChapterResponse.builder()
                .id(chapter.getId())
                .number(chapter.getNumber())
                .createdAt(chapter.getCreatedAt())
                .build();
    }

    @Transactional
    public List<ChapterResponse> getAllByComicId(String comicId) {
        Integer newComicId = Integer.parseInt(comicId);

        if (!comicRepository.existsById(newComicId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "comic not found");
        }
        var chapters = chapterRepository.findAllByComicId(newComicId);
        return chapters.stream()
                .sorted((c1, c2) -> c2.getCreatedAt().compareTo(c1.getCreatedAt()))
                .map(ch -> ChapterResponse
                        .builder()
                        .id(ch.getId())
                        .number(ch.getNumber())
                        .createdAt(ch.getCreatedAt())
                        .build())
                .toList();
    }

    @Transactional
    public void delete(String comicId, String chapterId) {
        Integer newComicId = Integer.parseInt(comicId);
        Long newChapterId = (long) Integer.parseInt(chapterId);

        if (!comicRepository.existsById(newComicId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "comic not found");
        }
        if (!chapterRepository.existsById(newChapterId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "chapter not found");
        }
        chapterRepository.deleteById(newChapterId);
    }
}
