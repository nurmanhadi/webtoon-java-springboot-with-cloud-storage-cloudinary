package com.nurman.webtoon.model.comic;

import java.time.LocalDateTime;
import java.util.List;

import com.nurman.webtoon.model.chapter.ChapterResponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ComicResponse {
    private Integer id;
    private String cover;
    private String title;
    private String synopsis;
    private String author;
    private String artist;
    private String type;
    private String url;
    private LocalDateTime createdAt;
    private List<ChapterResponse> chapters;

}
