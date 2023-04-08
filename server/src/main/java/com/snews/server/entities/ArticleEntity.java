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

    @OneToOne(fetch = FetchType.EAGER)
    private ImageEntity image;

    @OneToOne(fetch = FetchType.EAGER)
    private ImageEntity thumbnail;

    private String imageSource;

    @Column(columnDefinition = "TEXT")
    private String content;

    private String author;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "category", referencedColumnName = "article_categories_id")
    private Set<ArticleCategoryEntity> categories = new HashSet<>();

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

    public Set<ArticleCategoryEntity> getCategories() {
        return categories;
    }

    public ArticleEntity setCategories(Set<ArticleCategoryEntity> categories) {
        this.categories = categories;
        return this;
    }

    public String getHref() {
        return href;
    }

    public ArticleEntity setHref(String href) {
        this.href = href;
        return this;
    }

    public void addCategory(ArticleCategoryEntity category) {
        this.categories.add(category);
    }

    public ImageEntity getImage() {
        return image;
    }

    public ArticleEntity setImage(ImageEntity image) {
        this.image = image;
        return this;
    }

    public ImageEntity getThumbnail() {
        return thumbnail;
    }

    public ArticleEntity setThumbnail(ImageEntity thumbnail) {
        this.thumbnail = thumbnail;
        return this;
    }
}
