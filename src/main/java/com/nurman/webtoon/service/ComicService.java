package com.nurman.webtoon.service;

import java.time.Instant;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.nurman.webtoon.entity.Comic;
import com.nurman.webtoon.model.PageResponse;
import com.nurman.webtoon.model.category.CategoryResponse;
import com.nurman.webtoon.model.chapter.ChapterResponse;
import com.nurman.webtoon.model.comic.ComicAddRequest;
import com.nurman.webtoon.model.comic.ComicResponse;
import com.nurman.webtoon.model.comic.ComicUpdateRequest;
import com.nurman.webtoon.model.comicCategory.ComicCategoryResponse;
import com.nurman.webtoon.repository.ComicRepository;

@Service
public class ComicService {
        @Autowired
        private ComicRepository comicRepository;

        @Autowired
        private ValidationService validationService;

        @Autowired
        private ImageService imageService;

        @Autowired
        private CloudinaryService cloudinaryService;

        @Transactional
        public void add(MultipartFile cover, ComicAddRequest request) {
                validationService.validate(request);
                imageService.validate(cover);
                String filename = String.valueOf(Instant.now().toEpochMilli());
                Comic comic = new Comic();
                comic.setCover(filename);
                comic.setTitle(request.getTitle());
                comic.setSynopsis(request.getSynopsis());
                comic.setAuthor(request.getAuthor());
                comic.setArtist(request.getArtist());
                comic.setType(request.getType().toUpperCase());

                String urlCover = cloudinaryService.uploadImage(cover, filename);
                comic.setUrl(urlCover);
                comicRepository.save(comic);
        }

        @Transactional
        public void update(String comicId, MultipartFile cover, ComicUpdateRequest request) {
                validationService.validate(request);
                Integer newId = Integer.parseInt(comicId);
                Comic comic = comicRepository.findById(newId)
                                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                                                "comic not found"));

                if (Objects.nonNull(cover)) {
                        imageService.validate(cover);
                        String coverUrl = cloudinaryService.uploadImage(cover, comic.getCover());

                        comic.setUrl(coverUrl);
                }
                if (Objects.nonNull(request.getTitle())) {
                        comic.setTitle(request.getTitle());
                }
                if (Objects.nonNull(request.getSynopsis())) {
                        comic.setSynopsis(request.getSynopsis());
                }
                if (Objects.nonNull(request.getAuthor())) {
                        comic.setAuthor(request.getAuthor());
                }
                if (Objects.nonNull(request.getArtist())) {
                        comic.setArtist(request.getArtist());
                }
                if (Objects.nonNull(request.getType())) {
                        comic.setType(request.getType().toUpperCase());
                }
                comicRepository.save(comic);
        }

        @Transactional(readOnly = true)
        public ComicResponse getById(String comicId) {
                Integer newId = Integer.parseInt(comicId);
                Comic comic = comicRepository.findById(newId)
                                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                                                "comic not found"));
                return ComicResponse.builder()
                                .id(comic.getId())
                                .cover(comic.getCover())
                                .title(comic.getTitle())
                                .synopsis(comic.getSynopsis())
                                .author(comic.getAuthor())
                                .artist(comic.getArtist())
                                .type(comic.getType())
                                .url(comic.getUrl())
                                .createdAt(comic.getCreatedAt())
                                .comicCategories(comic.getComicCategories().stream().map(cc -> ComicCategoryResponse
                                                .builder()
                                                .id(cc.getId())
                                                .category(CategoryResponse.builder()
                                                                .id(cc.getCategory().getId())
                                                                .name(cc.getCategory().getName())
                                                                .build())
                                                .build()).toList())
                                .build();
        }

        @Transactional(readOnly = true)
        public PageResponse<ComicResponse> getAll(String page, String size) {
                Integer newPage = Integer.parseInt(page) - 1;
                Integer newSize = Integer.parseInt(size);
                Pageable pagging = PageRequest.of(newPage, newSize, Sort.by("createdAt").descending());
                var comics = comicRepository.findAll(pagging);

                List<ComicResponse> response = comics.getContent().stream()
                                .sorted((c1, c2) -> c2.getCreatedAt().compareTo(c1.getCreatedAt()))
                                .map(comic -> ComicResponse.builder()
                                                .id(comic.getId())
                                                .cover(comic.getCover())
                                                .title(comic.getTitle())
                                                .synopsis(comic.getSynopsis())
                                                .author(comic.getAuthor())
                                                .artist(comic.getArtist())
                                                .type(comic.getType())
                                                .url(comic.getUrl())
                                                .createdAt(comic.getCreatedAt())
                                                .chapters(comic.getChapters().stream()
                                                                .sorted((c1, c2) -> c2.getCreatedAt()
                                                                                .compareTo(c1.getCreatedAt()))
                                                                .limit(2)
                                                                .map(ch -> ChapterResponse
                                                                                .builder()
                                                                                .id(ch.getId())
                                                                                .number(ch.getNumber())
                                                                                .createdAt(ch.getCreatedAt())
                                                                                .build())
                                                                .toList())
                                                .build())
                                .toList();
                return PageResponse.<ComicResponse>builder()
                                .contents(response)
                                .page(newPage + 1)
                                .size(newSize)
                                .totalPages(comics.getTotalPages())
                                .totalEmelents(comics.getTotalElements())
                                .build();
        }

        @Transactional
        public void delete(String comicId) {
                Integer newId = Integer.parseInt(comicId);
                var comic = comicRepository.findById(newId)
                                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                                                "comic not found"));
                cloudinaryService.deleteImage(comic.getCover());
                comicRepository.deleteById(newId);
        }

        @Transactional(readOnly = true)
        public PageResponse<ComicResponse> search(String keyword, String page, String size) {
                Integer newPage = Integer.parseInt(page) - 1;
                Integer newSize = Integer.parseInt(size);
                Pageable pageable = PageRequest.of(newPage, newSize);
                var comics = comicRepository.findAllByTitleContainingIgnoreCase(keyword, pageable);

                List<ComicResponse> comicResponse = comics.stream()
                                .map(c -> ComicResponse.builder()
                                                .id(c.getId())
                                                .cover(c.getCover())
                                                .title(c.getTitle())
                                                .synopsis(c.getSynopsis())
                                                .author(c.getAuthor())
                                                .artist(c.getArtist())
                                                .type(c.getType())
                                                .url(c.getUrl())
                                                .createdAt(c.getCreatedAt())
                                                .build())
                                .toList();
                return PageResponse.<ComicResponse>builder()
                                .contents(comicResponse)
                                .page(newPage + 1)
                                .size(newSize)
                                .totalPages(comics.getTotalPages())
                                .totalEmelents(comics.getTotalElements())
                                .build();
        }

        @Transactional(readOnly = true)
        public PageResponse<ComicResponse> getAllByType(String type, String page, String size) {
                Integer newPage = Integer.parseInt(page) - 1;
                Integer newSize = Integer.parseInt(size);
                Pageable pageable = PageRequest.of(newPage, newSize);
                var comics = comicRepository.findAllByType(type, pageable);

                List<ComicResponse> comicResponse = comics.stream()
                                .map(c -> ComicResponse.builder()
                                                .id(c.getId())
                                                .cover(c.getCover())
                                                .title(c.getTitle())
                                                .synopsis(c.getSynopsis())
                                                .author(c.getAuthor())
                                                .artist(c.getArtist())
                                                .type(c.getType())
                                                .url(c.getUrl())
                                                .createdAt(c.getCreatedAt())
                                                .build())
                                .toList();
                return PageResponse.<ComicResponse>builder()
                                .contents(comicResponse)
                                .page(newPage + 1)
                                .size(newSize)
                                .totalPages(comics.getTotalPages())
                                .totalEmelents(comics.getTotalElements())
                                .build();
        }
}
