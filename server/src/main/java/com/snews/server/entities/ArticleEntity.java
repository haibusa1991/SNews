package com.snews.server.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "articles")
public class ArticleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String heading;

    private LocalDateTime published;

    @ManyToOne
    private PictureEntity picture;

    private String pictureSource;

    private String content;

    private String author;

    @ManyToMany(mappedBy = "articles")
    private Set<ArticleTagEntity> tags;

    public String getHeading() {
        return heading;
    }

    public ArticleEntity setHeading(String heading) {
        this.heading = heading;
        return this;
    }

    public LocalDateTime getPublished() {
        return published;
    }

    public ArticleEntity setPublished(LocalDateTime published) {
        this.published = published;
        return this;
    }

    public PictureEntity getPicture() {
        return picture;
    }

    public ArticleEntity setPicture(PictureEntity picture) {
        this.picture = picture;
        return this;
    }

    public String getPictureSource() {
        return pictureSource;
    }

    public ArticleEntity setPictureSource(String pictureSource) {
        this.pictureSource = pictureSource;
        return this;
    }

    public String getContent() {
        return content;
    }

    public ArticleEntity setContent(String content) {
        this.content = content;
        return this;
    }

    public String getAuthor() {
        return author;
    }

    public ArticleEntity setAuthor(String author) {
        this.author = author;
        return this;
    }

    public Set<ArticleTagEntity> getTags() {
        return tags;
    }

    public ArticleEntity setTags(Set<ArticleTagEntity> tags) {
        this.tags = tags;
        return this;
    }
}
