package com.nurman.webtoon.model.comic;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ComicAddRequest {

    @NotBlank
    @Size(max = 100)
    private String title;

    @NotBlank
    private String synopsis;

    @NotBlank
    @Size(max = 50)
    private String author;

    @NotBlank
    @Size(max = 50)
    private String artist;

    @NotBlank
    @Size(max = 15)
    private String type;
}
