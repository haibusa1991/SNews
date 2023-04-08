package com.snews.server.dto;

public class ArticleDto {
    private String heading;
    private String published;
    private String image;
    private String thumbnail;
    private String imageSource;
    private String[] content;
    private String author;
    private String[] categories;

    public String getHeading() {
        return heading;
    }

    public ArticleDto setHeading(String heading) {
        this.heading = heading;
        return this;
    }

    public String getImage() {
        return image;
    }

    public ArticleDto setImage(String image) {
        this.image = image;
        return this;
    }

    public String getImageSource() {
        return imageSource;
    }

    public ArticleDto setImageSource(String imageSource) {
        this.imageSource = imageSource;
        return this;
    }

    public String[] getContent() {
        return content;
    }

    public ArticleDto setContent(String[] content) {
        this.content = content;
        return this;
    }

    public String getAuthor() {
        return author;
    }

    public ArticleDto setAuthor(String author) {
        this.author = author;
        return this;
    }

    public String[] getCategories() {
        return categories;
    }

    public ArticleDto setCategories(String[] categories) {
        this.categories = categories;
        return this;
    }

    public String getPublished() {
        return published;
    }

    public ArticleDto setPublished(String published) {
        this.published = published;
        return this;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public ArticleDto setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
        return this;
    }
}
