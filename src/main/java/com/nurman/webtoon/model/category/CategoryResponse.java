package com.nurman.webtoon.model.category;

import java.util.List;

import com.nurman.webtoon.model.comicCategory.ComicCategoryResponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryResponse {
    private Integer id;
    private String name;
    private List<ComicCategoryResponse> comicCategories;
}
