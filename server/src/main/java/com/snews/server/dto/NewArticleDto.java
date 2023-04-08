package com.snews.server.dto;

import org.springframework.web.multipart.MultipartFile;

public class NewArticleDto {
    private String content;
    private String author;
    private String[] categories;
    private String heading;
    private String imageSource;

    private MultipartFile imageFile;

    public String getContent() {
        return content;
    }

    public NewArticleDto setContent(String content) {
        this.content = content;
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

    public String getImageSource() {
        return imageSource;
    }

    public NewArticleDto setImageSource(String imageSource) {
        this.imageSource = imageSource;
        return this;
    }

    public MultipartFile getImageFile() {
        return imageFile;
    }

    public NewArticleDto setImageFile(MultipartFile imageFile) {
        this.imageFile = imageFile;
        return this;
    }
}
