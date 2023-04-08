package com.snews.server.services.articleCategory;

import com.snews.server.entities.ArticleCategoryEntity;
import com.snews.server.entities.ArticleEntity;
import com.snews.server.entities.ImageEntity;
import com.snews.server.enumeration.ArticleCategoryEnum;
import com.snews.server.repositories.ArticleCategoryRepository;
import com.snews.server.services.article.ArticleServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ArticleCategoryServiceImplTest {
    private ArticleCategoryEntity mockCategoryEntity = new ArticleCategoryEntity()
            .setCategory(ArticleCategoryEnum.POLITICS)
            .setArticles(Set.of(new ArticleEntity()));


    @Mock
    private ArticleCategoryRepository mockArticleCategoryRepository;

    ArticleCategoryServiceImpl tested;

    @BeforeEach
    void setUp() {
        this.tested = new ArticleCategoryServiceImpl(
                mockArticleCategoryRepository);
    }
    @Test
    void initCategoriesReturnsIfRepoContainsEntities() {
        when(mockArticleCategoryRepository.count()).thenReturn(10L);
        tested.initCategories();
        verify(mockArticleCategoryRepository,times(0)).save(any());
    }

    @Test
    void initCategoriesSavesAllEnums() {
        when(mockArticleCategoryRepository.count()).thenReturn(0L);
        tested.initCategories();
        verify(mockArticleCategoryRepository,times(1)).saveAll(any());
    }

    @Test
    void getAllCategories() {
        when(mockArticleCategoryRepository.findAll()).thenReturn(List.of(this.mockCategoryEntity));

        int expected = 1;
        int actual = tested.getAllCategories().size();

        Assertions.assertEquals(expected,actual);
    }

    @Test
    void getCategoryReturnsNullIfNotContaining() {
        when(mockArticleCategoryRepository.getArticleCategoryEntityByCategory(any())).thenReturn(null);
        Assertions.assertNull(tested.getCategory(any()));
    }

    @Test
    void getCategoryReturnsCorrect() {
        when(mockArticleCategoryRepository.getArticleCategoryEntityByCategory(any())).thenReturn(mockCategoryEntity);
        ArticleCategoryEntity expected = this.mockCategoryEntity;
        ArticleCategoryEntity actual = tested.getCategory(any());

        Assertions.assertEquals(expected,actual);
    }
}