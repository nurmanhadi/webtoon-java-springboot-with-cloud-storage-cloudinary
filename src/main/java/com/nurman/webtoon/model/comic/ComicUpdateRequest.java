package com.nurman.webtoon.model.comic;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ComicUpdateRequest {

    @Nullable
    @Size(max = 100)
    private String title;

    @Nullable
    private String synopsis;

    @Nullable
    @Size(max = 50)
    private String author;

    @Nullable
    @Size(max = 50)
    private String artist;

    @Nullable
    @Size(max = 15)
    private String type;
}
