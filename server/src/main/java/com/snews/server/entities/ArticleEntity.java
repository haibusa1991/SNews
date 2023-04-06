package com.snews.server.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "articles")
public class ArticleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(unique = true)
    private String heading;

    private LocalDateTime published;

    @Column(columnDefinition = "TEXT")
    private String image;

    @Column(columnDefinition = "TEXT")
    private String thumbnail;

    private String imageSource;

    @Column(columnDefinition = "TEXT")
    private String content;

    private String author;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "tags", referencedColumnName = "article_tags_id")
    private Set<ArticleCategoryEntity> tags = new HashSet<>();

    private String href;

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

    public String getImage() {
        return image;
    }

    public ArticleEntity setImage(String picture) {
        this.image = picture;
        return this;
    }

    public String getImageSource() {
        return imageSource;
    }

    public ArticleEntity setImageSource(String pictureSource) {
        this.imageSource = pictureSource;
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

    public Set<ArticleCategoryEntity> getTags() {
        return tags;
    }

    public ArticleEntity setTags(Set<ArticleCategoryEntity> tags) {
        this.tags = tags;
        return this;
    }

    public String getHref() {
        return href;
    }

    public ArticleEntity setHref(String href) {
        this.href = href;
        return this;
    }

    public void addTag(ArticleCategoryEntity tag) {
        this.tags.add(tag);
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public ArticleEntity setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
        return this;
    }
}
