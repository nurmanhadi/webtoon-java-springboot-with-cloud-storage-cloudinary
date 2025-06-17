package com.nurman.webtoon.service;

import java.time.Instant;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.nurman.webtoon.entity.Content;
import com.nurman.webtoon.model.content.ContentResponse;
import com.nurman.webtoon.repository.ChapterRepository;
import com.nurman.webtoon.repository.ComicRepository;
import com.nurman.webtoon.repository.ContentRepository;

import jakarta.transaction.Transactional;

@Service
public class ContentService {
    @Autowired
    private ContentRepository contentRepository;
    @Autowired
    private ComicRepository comicRepository;
    @Autowired
    private ChapterRepository chapterRepository;
    @Autowired
    private ImageService imageService;
    @Autowired
    private CloudinaryService cloudinaryService;

    @Transactional
    public void add(String comicId, String chapterId, MultipartFile content) {
        Integer newComicId = Integer.parseInt(comicId);
        Long newChapterId = (long) Integer.parseInt(chapterId);
        if (!comicRepository.existsById(newComicId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "comic not found");
        }
        var chapter = chapterRepository.findById(newChapterId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "chapter not found"));
        imageService.validate(content);

        String filename = String.valueOf(Instant.now().toEpochMilli());
        String url = cloudinaryService.uploadImage(content, filename);
        Content contentEnt = new Content();
        contentEnt.setFilename(filename);
        contentEnt.setUrl(url);
        contentEnt.setChapter(chapter);
        contentRepository.save(contentEnt);
    }

    @Transactional
    public void update(String comicId, String chapterId, String contentId, MultipartFile content) {
        Integer newComicId = Integer.parseInt(comicId);
        Long newChapterId = (long) Integer.parseInt(chapterId);
        Long newContentId = (long) Integer.parseInt(contentId);
        if (!comicRepository.existsById(newComicId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "comic not found");
        }
        if (!chapterRepository.existsById(newChapterId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "chapter not found");
        }
        var contentEnt = contentRepository.findById(newContentId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "content not found"));
        imageService.validate(content);

        String url = cloudinaryService.uploadImage(content, contentEnt.getFilename());
        contentEnt.setUrl(url);
        contentRepository.save(contentEnt);
    }

    @Transactional
    public List<ContentResponse> getAllByChapterId(String comicId, String chapterId) {
        Integer newComicId = Integer.parseInt(comicId);
        Long newChapterId = (long) Integer.parseInt(chapterId);
        if (!comicRepository.existsById(newComicId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "comic not found");
        }
        if (!chapterRepository.existsById(newChapterId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "chapter not found");
        }
        var contents = contentRepository.findAllByChapterId(newChapterId);
        var response = contents.stream().map(c -> ContentResponse
                .builder()
                .id(c.getId())
                .filename(c.getFilename())
                .url(c.getUrl())
                .createdAt(c.getCreatedAt())
                .build()).toList();
        return response;
    }

    public void delete(String comicId, String chapterId, String contentId) {
        Integer newComicId = Integer.parseInt(comicId);
        Long newChapterId = (long) Integer.parseInt(chapterId);
        Long newContentId = (long) Integer.parseInt(contentId);
        if (!comicRepository.existsById(newComicId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "comic not found");
        }
        if (!chapterRepository.existsById(newChapterId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "chapter not found");
        }
        var content = contentRepository.findById(newContentId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "content not found"));
        cloudinaryService.deleteImage(content.getFilename());
        contentRepository.deleteById(newContentId);
    }
}
