package com.snews.server.services.articleCategory;

import com.snews.server.entities.ArticleCategoryEntity;
import com.snews.server.enumeration.ArticleCategoryEnum;
import com.snews.server.repositories.ArticleCategoryRepository;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class ArticleCategoryServiceImpl implements ArticleCategoryService {

    private final ArticleCategoryRepository categoryRepository;

    public ArticleCategoryServiceImpl(ArticleCategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void initCategories() {
        if (this.categoryRepository.count() > 0) return;

        this.categoryRepository.saveAll(
                Arrays.stream(ArticleCategoryEnum.values())
                        .map(ArticleCategoryEntity::new)
                        .toList());
    }

    @Override
    public List<ArticleCategoryEntity> getAllCategories() {
        return this.categoryRepository.findAll();
    }

    @Override
    public ArticleCategoryEntity getCategory(ArticleCategoryEnum categoryEnum) {
        return this.categoryRepository.getArticleCategoryEntityByCategory(categoryEnum);
    }

}
