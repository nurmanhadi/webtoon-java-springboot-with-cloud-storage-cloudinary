package com.nurman.webtoon.model.content;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ContentResponse {
    private Long id;
    private String filename;
    private String url;
    private LocalDateTime createdAt;
}
