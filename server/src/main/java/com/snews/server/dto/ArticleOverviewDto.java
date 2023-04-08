package com.snews.server.dto;

public class ArticleOverviewDto {
    private String href;
    private String thumbnail;
    private String published;
    private String heading;

    public String getHref() {
        return href;
    }

    public ArticleOverviewDto setHref(String href) {
        this.href = href;
        return this;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public ArticleOverviewDto setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
        return this;
    }

    public String getPublished() {
        return published;
    }

    public ArticleOverviewDto setPublished(String published) {
        this.published = published;
        return this;
    }

    public String getHeading() {
        return heading;
    }

    public ArticleOverviewDto setHeading(String heading) {
        this.heading = heading;
        return this;
    }


}
