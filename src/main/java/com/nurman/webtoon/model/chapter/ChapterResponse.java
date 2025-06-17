package com.nurman.webtoon.model.chapter;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChapterResponse {
    private Long id;
    private Long number;
    private LocalDateTime createdAt;
}
