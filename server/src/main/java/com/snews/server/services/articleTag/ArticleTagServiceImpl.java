package com.snews.server.services.articleTag;

import com.snews.server.entities.ArticleTagEntity;
import com.snews.server.enumeration.ArticleTagEnum;
import com.snews.server.repositories.ArticleTagRepository;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class ArticleTagServiceImpl implements ArticleTagService {

    private final ArticleTagRepository tagRepository;

    public ArticleTagServiceImpl(ArticleTagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    @Override
    public void initTags() {
        if (this.tagRepository.count() > 0) return;

        this.tagRepository.saveAll(
                Arrays.stream(ArticleTagEnum.values())
                        .map(ArticleTagEntity::new)
                        .toList());
    }

    @Override
    public List<ArticleTagEntity> getAllTags() {
        return this.tagRepository.findAll();
    }

    @Override
    public ArticleTagEntity getTag(ArticleTagEnum tagEnum) {
        return this.tagRepository.getArticleTagEntityByTag(tagEnum);
    }

}
