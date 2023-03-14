package com.snews.server.entities;

import com.snews.server.enumeration.ArticleTagEnum;
import jakarta.persistence.*;

import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "article_tags")
public class ArticleTagEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Enumerated(EnumType.STRING)
    private ArticleTagEnum tag;

    @ManyToMany(fetch = FetchType.EAGER,mappedBy = "tags")
    private Set<ArticleEntity> articles;

    public ArticleTagEnum getTag() {
        return tag;
    }

    public ArticleTagEntity setTag(ArticleTagEnum tag) {
        this.tag = tag;
        return this;
    }

    public ArticleTagEntity() {
    }

    public ArticleTagEntity(ArticleTagEnum tag) {
        this.tag = tag;
    }

    public Set<ArticleEntity> getArticles() {
        return articles;
    }

    public ArticleTagEntity setArticles(Set<ArticleEntity> articles) {
        this.articles = articles;
        return this;
    }
}
