package com.nurman.webtoon.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PageResponse<T> {
    private List<T> contents;
    private int page;
    private int size;
    private int totalPages;
    private long totalEmelents;
}
