package com.nurman.webtoon.model.comicCategory;

import com.nurman.webtoon.model.category.CategoryResponse;
import com.nurman.webtoon.model.comic.ComicResponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ComicCategoryResponse {
    private Long id;
    private ComicResponse comic;
    private CategoryResponse category;
}
