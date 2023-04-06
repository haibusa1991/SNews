package com.snews.server.services.article;

import com.snews.server.dto.ArticleDto;
import com.snews.server.dto.ArticleOverviewDto;
import com.snews.server.dto.NewArticleDto;
import com.snews.server.entities.ArticleEntity;
import com.snews.server.entities.ArticleCategoryEntity;
import com.snews.server.enumeration.ArticleCategoryEnum;
import com.snews.server.exceptions.InternalServerErrorException;
import com.snews.server.exceptions.MalformedDataException;
import com.snews.server.repositories.ArticleRepository;
import com.snews.server.services.articleTag.ArticleTagService;
import com.snews.server.services.file.FileService;
import net.coobird.thumbnailator.Thumbnails;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;

@Service
public class ArticleServiceImpl implements ArticleService {

    private final FileService fileService;
    private final ArticleTagService articleCategoryService;
    private final ArticleRepository articleRepository;
    private final ModelMapper modelMapper;


    public ArticleServiceImpl(FileService fileService, ArticleTagService articleCategoryService, ArticleRepository articleRepository, ModelMapper modelMapper) {
        this.fileService = fileService;
        this.articleCategoryService = articleCategoryService;
        this.articleRepository = articleRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public String addArticle(NewArticleDto dto) throws InternalServerErrorException, MalformedDataException {
        Set<ArticleCategoryEntity> categories = new HashSet<>();
        for (String cat : dto.getCategories()) {
            ArticleCategoryEntity category = this.articleCategoryService.getCategory(ArticleCategoryEnum.valueOf(cat));

            if (category == null) {
                throw new MalformedDataException("Invalid categories.");
            }
            categories.add(category);
        }

        String articleImage = this.getBase64Image(dto.getImageFile(), 960, 720);
        String articleThumbnail = this.getBase64Image(dto.getImageFile(), 400, 240);

        ArticleEntity article = new ArticleEntity();
        article.setHeading(dto.getHeading())
                .setPublished(LocalDateTime.now())
                .setImage(articleImage)
                .setThumbnail(articleThumbnail)
                .setImageSource(dto.getImageSource())
                .setContent(dto.getContent())
                .setAuthor(dto.getAuthor())
                .setHref(getHref(dto.getHeading()));

        try {
            ArticleEntity persisted = this.articleRepository.save(article);
            persisted.setTags(categories);
            this.articleRepository.save(persisted);
            this.articleRepository.save(article); //TODO check if second persistence is required
            return article.getHref();
        } catch (Exception e) {
            throw new InternalServerErrorException("Error saving article to database.");
        }
    }

    private String getBase64Image(MultipartFile imageFile, int width, int height ){
        try {
            ByteArrayOutputStream imageBytes = new ByteArrayOutputStream();

            Thumbnails.of(new ByteArrayInputStream(imageFile.getBytes()))
                    .size(width, height)
                    .keepAspectRatio(true)
                    .outputQuality(0.8)
                    .toOutputStream(imageBytes);

            return Base64.getUrlEncoder().encodeToString(imageBytes.toByteArray());
        } catch (IOException e) {
            return "";
        }
    }

    private String getHref(String heading) {
        Map<Character, String> chars = new HashMap<>();

        chars.put('а', "a");
        chars.put('б', "b");
        chars.put('в', "v");
        chars.put('г', "g");
        chars.put('д', "d");
        chars.put('е', "e");
        chars.put('ж', "zh");
        chars.put('з', "z");
        chars.put('и', "i");
        chars.put('й', "y");
        chars.put('к', "k");
        chars.put('л', "l");
        chars.put('м', "m");
        chars.put('н', "n");
        chars.put('о', "o");
        chars.put('п', "p");
        chars.put('р', "r");
        chars.put('с', "s");
        chars.put('т', "t");
        chars.put('у', "u");
        chars.put('ф', "f");
        chars.put('х', "h");
        chars.put('ц', "ts");
        chars.put('ч', "ch");
        chars.put('ш', "sh");
        chars.put('щ', "sht");
        chars.put('ъ', "a");
        chars.put('ь', "y");
        chars.put('ю', "yu");
        chars.put('я', "ya");
        chars.put(' ', "-");

        char[] input = heading.toLowerCase().toCharArray();

        StringBuilder sb = new StringBuilder();
        for (char c : input) {
            if (c >= 97 && c <= 122) {
                sb.append(c);
                continue;
            }

            String replacement = chars.get(c);
            if (replacement != null) {
                sb.append(replacement);
            }
        }
        return sb.toString();
    }

    @Override
    public String[] getArticleCategories() {
        return this.articleCategoryService
                .getAllTags()
                .stream()
                .map(e -> e.getCategory().name())
                .toArray(String[]::new);
    }

    @Override
    public ArticleDto getArticle(String href) {
        ArticleEntity article = this.articleRepository.getArticleEntityByHref(href);
        ArticleDto dto = this.modelMapper.map(article, ArticleDto.class);

        boolean isAuthenticated = SecurityContextHolder.getContext().getAuthentication().isAuthenticated();

        if (!isAuthenticated) {
            dto.setContent(new String[]{dto.getContent()[0]});
        }


        return this.modelMapper.map(article, ArticleDto.class);
    }

    @Override
    public ArticleOverviewDto[] getRecentArticles(int articleCount) {
        List<ArticleEntity> topArticles = this.articleRepository.getTopArticles(articleCount);
        if (topArticles.isEmpty()) {
            return new ArticleOverviewDto[0];
        }

        ArticleOverviewDto[] dtos = topArticles.stream()
                .map(article -> this.modelMapper.map(article, ArticleOverviewDto.class))
                .toArray(ArticleOverviewDto[]::new);


        if (dtos.length < articleCount) {
            ArticleOverviewDto[] filledDtos = new ArticleOverviewDto[articleCount];
            for (int i = 0; i < articleCount; i++) {
                filledDtos[i] = dtos[i % dtos.length];
            }
            return filledDtos;
        }

        return dtos;
    }

    @Override
//    TODO implement pagination of results
    public ArticleOverviewDto[] getTodayArticles() {
        LocalDateTime now = LocalDateTime.now();

        int dayOfMonth = now.getDayOfMonth();
        Month month = now.getMonth();
        int year = now.getYear();

        LocalDateTime today = LocalDateTime.of(year, month, dayOfMonth, 0, 0, 0);

        List<ArticleEntity> todayArticles = this.articleRepository.findAllByPublishedAfterOrderByPublishedDesc(today);

        if (todayArticles.isEmpty()) {
            return new ArticleOverviewDto[0];
        }

        return todayArticles.stream()
                .map(article -> this.modelMapper.map(article, ArticleOverviewDto.class))
                .toArray(ArticleOverviewDto[]::new);
    }

    @Override
    public ArticleOverviewDto[] getArticlesByCategory(String category) {
        boolean isValidTag = Arrays.stream(ArticleCategoryEnum.values())
                .map(Enum::name)
                .anyMatch(e -> e.equalsIgnoreCase(category));

        if (!isValidTag) {
            return new ArticleOverviewDto[0];
        }

        ArticleCategoryEnum tagEnum = ArticleCategoryEnum.valueOf(category.toUpperCase());
        ArticleCategoryEntity tag = this.articleCategoryService.getCategory(tagEnum);

        List<ArticleEntity> articles = this.articleRepository.findAllByTagsContainingIgnoreCaseOrderByPublishedDesc(tag);

        return articles.stream()
                .map(article -> this.modelMapper.map(article, ArticleOverviewDto.class))
                .toArray(ArticleOverviewDto[]::new);
    }
}
