package com.snews.server.services.article;

import com.snews.server.dto.ArticleDto;
import com.snews.server.dto.ArticleOverviewDto;
import com.snews.server.dto.NewArticleDto;
import com.snews.server.entities.ArticleEntity;
import com.snews.server.entities.ArticleTagEntity;
import com.snews.server.enumeration.ArticleTagEnum;
import com.snews.server.exceptions.InternalServerErrorException;
import com.snews.server.exceptions.MalformedDataException;
import com.snews.server.repositories.ArticleRepository;
import com.snews.server.services.articleTag.ArticleTagService;
import com.snews.server.services.file.FileService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class ArticleServiceImpl implements ArticleService {

    private final FileService fileService;
    private final ArticleTagService articleTagService;
    private final ArticleRepository articleRepository;
    private final ModelMapper modelMapper;


    public ArticleServiceImpl(FileService fileService, ArticleTagService articleTagService, ArticleRepository articleRepository, ModelMapper modelMapper) {
        this.fileService = fileService;
        this.articleTagService = articleTagService;
        this.articleRepository = articleRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public String save(NewArticleDto dto) throws InternalServerErrorException, MalformedDataException {

        String pictureLink;
        try {
            byte[] file = dto.getPictureFile().getBytes();
            pictureLink = this.fileService.savePictureToDisk(file);
            this.fileService.generateThumbnail(file, pictureLink);
        } catch (Exception e) {
            throw new InternalServerErrorException("Unable to save image");
        }

        Set<ArticleTagEntity> tags = new HashSet<>();
        for (String category : dto.getCategories()) {
            ArticleTagEntity tag = this.articleTagService.getTag(ArticleTagEnum.valueOf(category));

            if (tag == null) {
                throw new MalformedDataException("invalid tags");
            }
            tags.add(tag);
        }

        ArticleEntity article = new ArticleEntity();
        article.setHeading(dto.getHeading())
                .setPublished(LocalDateTime.now())
                .setPicture(pictureLink)
                .setPictureSource(dto.getPictureSource())
                .setContent(dto.getArticle())
                .setAuthor(dto.getAuthor())
                .setHref(getHref(dto.getHeading()));

        ArticleEntity persisted = this.articleRepository.save(article);
        persisted.setTags(tags);
        this.articleRepository.save(persisted);

        this.articleRepository.save(article);

        return article.getHref();
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
        return this.articleTagService
                .getAllTags()
                .stream()
                .map(e -> e.getTag().name())
                .toArray(String[]::new);
    }

    @Override
    public ArticleDto getArticle(String href) {
        ArticleEntity article = this.articleRepository.getArticleEntityByHref(href);

        ArticleDto dto = this.modelMapper.map(article, ArticleDto.class);

        return dto;
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
}
