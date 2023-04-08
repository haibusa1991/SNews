package com.snews.server.services.article;

import com.snews.server.dto.ArticleDto;
import com.snews.server.dto.ArticleOverviewDto;
import com.snews.server.dto.NewArticleDto;
import com.snews.server.entities.ArticleCategoryEntity;
import com.snews.server.entities.ArticleEntity;
import com.snews.server.entities.ImageEntity;
import com.snews.server.enumeration.ArticleCategoryEnum;
import com.snews.server.exceptions.InternalServerErrorException;
import com.snews.server.exceptions.MalformedDataException;
import com.snews.server.repositories.ArticleRepository;
import com.snews.server.services.articleCategory.ArticleCategoryService;
import com.snews.server.services.image.ImageService;
import org.hibernate.HibernateError;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ArticleServiceImplTest {

    @Mock
    ArticleCategoryService mockArticleCategoryService;

    @Mock
    ArticleRepository mockArticleRepository;

    @Mock
    ImageService mockImageService;

    @Mock
    ModelMapper mockModelMapper;

    @Mock
    SecurityContextHolder mockContext = new SecurityContextHolder();

    @Mock
    Authentication authentication;

    @Mock
    SecurityContext securityContext;

    @InjectMocks
    @Spy
    ArticleServiceImpl articleService;


    private final LocalDateTime mockTime = LocalDateTime.of(2000, 1, 1, 0, 0, 0, 0);

    private final NewArticleDto mockNewArticleDto = new NewArticleDto()
            .setContent("paragraph 1\nparagraph 2")
            .setAuthor("test author")
            .setCategories(new String[]{"POLITICS"})
            .setHeading("test heading")
            .setImageSource("test image source")
            .setImageFile(new MockMultipartFile("test file.jpg", new byte[0]));

    private final ImageEntity mockImageEntity = new ImageEntity()
            .setData(new byte[0]);

    private final ArticleEntity mockArticleEntity = new ArticleEntity()
            .setPublished(this.mockTime)
            .setImage(mockImageEntity)
            .setThumbnail(mockImageEntity)
            .setImageSource("test image source")
            .setContent("paragraph 1\nparagraph 2")
            .setAuthor("test author")
            .setHref("test-heading")
            .setHeading("test heading");

    private final ArticleDto mockArticleDto = new ArticleDto()
            .setHeading("test heading")
            .setPublished(this.mockTime.toString())
            .setImage("12345678-abcd-ef12-9876-123456789abc")
            .setThumbnail("12345678-abcd-ef12-9876-123456789abc")
            .setImageSource("test image source")
            .setContent(new String[]{"paragraph 1", "paragraph 2"})
            .setAuthor("test author")
            .setCategories(new String[]{"POLITICS"});

    private final ArticleOverviewDto mockArticleOverviewDto = new ArticleOverviewDto()
            .setHref("test-heading")
            .setThumbnail("12345678-abcd-ef12-9876-123456789abc")
            .setPublished(this.mockTime.toString())
            .setHeading("test heading");

    @BeforeEach
    void setUp() {
        this.articleService = new ArticleServiceImpl(
                mockArticleCategoryService,
                mockArticleRepository,
                mockImageService,
                mockModelMapper);
    }

    @Test
    void addArticleThrowsMalformedDataExceptionWhenCategoryIsInvalid() {
        NewArticleDto malformedDto = this.mockNewArticleDto;
        malformedDto.setCategories(new String[]{"INVALID CATEGORY"});

        Assertions.assertThrows(MalformedDataException.class,
                () -> articleService.addArticle(malformedDto));
    }

    @Test
    void addArticleThrowsInternalServerErrorExceptionWhenRepositoryThrowsException() {

        when(this.mockArticleRepository.save(any(ArticleEntity.class))).thenThrow(new HibernateError(""));
        Assertions.assertThrows(InternalServerErrorException.class, () -> articleService.addArticle(mockNewArticleDto));
    }

    @Test
    void addArticleReturnsHrefOnSuccessfulSave() throws InternalServerErrorException, MalformedDataException {
        this.mockArticleRepository.save(this.mockArticleEntity);

        when(this.mockArticleRepository.save(any(ArticleEntity.class))).thenReturn(this.mockArticleEntity);

        String href = this.articleService.addArticle(mockNewArticleDto);
        Assertions.assertEquals("test-heading", href);
    }

    @Test
    void getArticleCategories() {
        ArticleCategoryEnum cat1Enum = ArticleCategoryEnum.POLITICS;
        ArticleCategoryEnum cat2Enum = ArticleCategoryEnum.BUSINESS;

        ArticleCategoryEntity cat1 = new ArticleCategoryEntity().setCategory(cat1Enum);
        ArticleCategoryEntity cat2 = new ArticleCategoryEntity().setCategory(cat2Enum);

        when(this.mockArticleCategoryService.getAllCategories()).thenReturn(List.of(cat1, cat2));

        String[] expected = new String[]{cat1Enum.name(), cat2Enum.name()};
        String[] actual = this.articleService.getArticleCategories();

        Assertions.assertArrayEquals(expected, actual);
    }

    @Test
    void getArticleReturnsTruncatedArticleWhenAnonymousUser() throws MalformedDataException {

        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        when(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).thenReturn("anonymousUser");
        when(this.mockArticleRepository.getArticleEntityByHref("test-heading")).thenReturn(this.mockArticleEntity);
        when(this.mockModelMapper.map(any(), any())).thenReturn(this.mockArticleDto);

        ArticleDto response = this.articleService.getArticle("test-heading");
        String[] actual = response.getContent();
        String[] expected = new String[]{"paragraph 1"};

        Assertions.assertArrayEquals(expected, actual);
    }

    @Test
    void getArticleThrowsMalformedDataExceptionOnInvalidHeading() {
        when(this.mockArticleRepository.getArticleEntityByHref("test-heading")).thenReturn(null);
        Assertions.assertThrows(MalformedDataException.class, () -> this.articleService.getArticle("test-heading"));
    }

    @Test
    void getArticleReturnsFullArticleWhenAuthenticatedUser() throws MalformedDataException {

        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        when(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).thenReturn("authenticatedUser");
        when(this.mockArticleRepository.getArticleEntityByHref("test-heading")).thenReturn(this.mockArticleEntity);
        when(this.mockModelMapper.map(any(), any())).thenReturn(this.mockArticleDto);

        ArticleDto response = this.articleService.getArticle("test-heading");
        String[] actual = response.getContent();
        String[] expected = new String[]{"paragraph 1", "paragraph 2"};

        Assertions.assertArrayEquals(expected, actual);
    }

    @Test
    void getRecentArticlesReturnsEmptyListOnNoResults() {
        int TARGET_ARTICLES = 10;
        int EXPECTED_ARTICLES = 0;

        when(this.mockArticleRepository.getTopArticles(anyInt())).thenReturn(List.of());
        ArticleOverviewDto[] recentArticles = this.articleService.getRecentArticles(TARGET_ARTICLES);
        Assertions.assertEquals(EXPECTED_ARTICLES, recentArticles.length);
    }

    @Test
    void getRecentArticlesReturnsFilledListOnInsufficientArticles() {
        int TARGET_ARTICLES = 10;

        when(this.mockArticleRepository.getTopArticles(anyInt())).thenReturn(List.of(this.mockArticleEntity));
        when(this.mockModelMapper.map(any(), any())).thenReturn(this.mockArticleOverviewDto);

        ArticleOverviewDto[] recentArticles = this.articleService.getRecentArticles(TARGET_ARTICLES);
        Assertions.assertEquals(TARGET_ARTICLES, recentArticles.length);
    }

    @Test
    void getRecentArticlesReturnsCorrectList() {
        int TARGET_ARTICLES = 1;

        when(this.mockArticleRepository.getTopArticles(anyInt())).thenReturn(List.of(this.mockArticleEntity));
        when(this.mockModelMapper.map(any(), any())).thenReturn(this.mockArticleOverviewDto);

        ArticleOverviewDto[] recentArticles = this.articleService.getRecentArticles(TARGET_ARTICLES);
        Assertions.assertEquals(TARGET_ARTICLES, recentArticles.length);
    }

    @Test
    void getTodayArticlesReturnsEmptyOnNoArticles() {
        int TARGET_ARTICLES = 0;
        when(this.mockArticleRepository.findAllByPublishedAfterOrderByPublishedDesc(any())).thenReturn(List.of());

        ArticleOverviewDto[] today = this.articleService.getTodayArticles();
        Assertions.assertEquals(TARGET_ARTICLES, today.length);
    }

    @Test
    void getTodayArticlesReturnsCorrect() {
        int TARGET_ARTICLES = 1;

        when(this.mockArticleRepository.findAllByPublishedAfterOrderByPublishedDesc(any())).thenReturn(List.of(this.mockArticleEntity));
        when(this.mockModelMapper.map(any(), any())).thenReturn(this.mockArticleOverviewDto);

        ArticleOverviewDto[] articles = this.articleService.getTodayArticles();
        Assertions.assertEquals(TARGET_ARTICLES, articles.length);
    }

    @Test
    void getArticlesByCategoryReturnsTodayArticles() {
        int TARGET_ARTICLES = 1;
        when(this.mockArticleRepository.findAllByPublishedAfterOrderByPublishedDesc(any())).thenReturn(List.of(this.mockArticleEntity));
        when(this.mockModelMapper.map(any(), any())).thenReturn(this.mockArticleOverviewDto);

        ArticleOverviewDto[] articles = this.articleService.getArticlesByCategory("today");

        Assertions.assertEquals(TARGET_ARTICLES, articles.length);
    }

    @Test
    void getArticlesByCategoryReturnsEmptyOnInvalidCategory() {
        ArticleOverviewDto[] articles = this.articleService.getArticlesByCategory("INVALID CATEGORY");
        Assertions.assertEquals(0, articles.length);
    }

    @Test
    void getArticlesByCategoryReturnsCorrectResults() {
        when(this.mockArticleCategoryService.getCategory(any())).thenReturn(new ArticleCategoryEntity());
        when(this.mockArticleRepository.findAllByCategoriesContainingIgnoreCaseOrderByPublishedDesc(any())).thenReturn(List.of(this.mockArticleEntity));
        when(this.mockModelMapper.map(any(), any())).thenReturn(mockArticleOverviewDto);

        ArticleOverviewDto[] actual = this.articleService.getArticlesByCategory("POLITICS");
        ArticleOverviewDto[] expected = new ArticleOverviewDto[]{mockArticleOverviewDto};

        Assertions.assertArrayEquals(expected, actual);
    }

}