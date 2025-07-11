package com.nurman.webtoon.entity;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "comics")
@Entity
public class Comic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 50)
    private String cover;

    @Column(length = 100)
    private String title;

    @Lob
    private String synopsis;

    @Column(length = 50)
    private String author;

    @Column(length = 50)
    private String artist;

    @Column(length = 15)
    private String type;

    @Column(length = 255)
    private String url;

    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "comic", fetch = FetchType.LAZY)
    private List<Chapter> chapters;

    @OneToMany(mappedBy = "comic", fetch = FetchType.LAZY)
    private List<ComicCategory> comicCategories;
}
