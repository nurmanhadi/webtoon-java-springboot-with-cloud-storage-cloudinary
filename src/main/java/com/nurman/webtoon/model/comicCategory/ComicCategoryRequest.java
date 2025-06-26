package com.nurman.webtoon.model.comicCategory;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ComicCategoryRequest {
    @NotNull
    private Integer comicId;

    @NotNull
    private Integer categoryId;
}
