package com.snews.server.services.articleTag;

import com.snews.server.entities.ArticleTagEntity;
import com.snews.server.entities.UserRoleEntity;
import com.snews.server.enumeration.AriticleTagEnum;
import com.snews.server.enumeration.UserRoleEnum;
import com.snews.server.repositories.ArticleTagRepository;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class ArticleTagServiceImpl implements ArticleTagService {

    private final ArticleTagRepository tagRepository;

    public ArticleTagServiceImpl(ArticleTagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    @Override
    public void initTags(){
        if (this.tagRepository.count() > 0) return;

        this.tagRepository.saveAll(
                Arrays.stream(AriticleTagEnum.values())
                        .map(ArticleTagEntity::new)
                        .toList());
    }
}
