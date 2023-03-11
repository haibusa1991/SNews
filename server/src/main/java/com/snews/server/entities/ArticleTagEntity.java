package com.snews.server.entities;

import com.snews.server.enumeration.AriticleTagEnum;
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
    private AriticleTagEnum tag;

    @ManyToMany
    private Set<ArticleEntity> articles;

    public AriticleTagEnum getTag() {
        return tag;
    }

    public ArticleTagEntity setTag(AriticleTagEnum tag) {
        this.tag = tag;
        return this;
    }

    public ArticleTagEntity() {
    }

    public ArticleTagEntity(AriticleTagEnum tag) {
        this.tag = tag;
    }
}
