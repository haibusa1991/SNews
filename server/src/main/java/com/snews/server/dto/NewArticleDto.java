package com.snews.server.dto;

import org.springframework.data.annotation.Transient;
import org.springframework.web.multipart.MultipartFile;

public class NewArticleDto {
    private String article;
    private String author;
    private String[] categories;
    private String heading;
    private String pictureSource;

//    @Transient
    private MultipartFile pictureFile;

    public String getArticle() {
        return article;
    }

    public NewArticleDto setArticle(String article) {
        this.article = article;
        return this;
    }

    public String getAuthor() {
        return author;
    }

    public NewArticleDto setAuthor(String author) {
        this.author = author;
        return this;
    }

    public String[] getCategories() {
        return categories;
    }

    public NewArticleDto setCategories(String[] categories) {
        this.categories = categories;
        return this;
    }

    public String getHeading() {
        return heading;
    }

    public NewArticleDto setHeading(String heading) {
        this.heading = heading;
        return this;
    }

    public String getPictureSource() {
        return pictureSource;
    }

    public NewArticleDto setPictureSource(String pictureSource) {
        this.pictureSource = pictureSource;
        return this;
    }

    public MultipartFile getPictureFile() {
        return pictureFile;
    }

    public NewArticleDto setPictureFile(MultipartFile pictureFile) {
        this.pictureFile = pictureFile;
        return this;
    }
}
