package com.snews.server.dto;

public class ArticleDto {
    private String heading;
    private String picture;
    private String pictureSource;
    private String content[];
    private String author;
    private String articleTags[];

    public String getHeading() {
        return heading;
    }

    public ArticleDto setHeading(String heading) {
        this.heading = heading;
        return this;
    }

    public String getPicture() {
        return picture;
    }

    public ArticleDto setPicture(String picture) {
        this.picture = picture;
        return this;
    }

    public String getPictureSource() {
        return pictureSource;
    }

    public ArticleDto setPictureSource(String pictureSource) {
        this.pictureSource = pictureSource;
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

    public String[] getArticleTags() {
        return articleTags;
    }

    public ArticleDto setArticleTags(String[] articleTags) {
        this.articleTags = articleTags;
        return this;
    }
}
