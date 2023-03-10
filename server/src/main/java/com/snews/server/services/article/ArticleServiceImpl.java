package com.snews.server.services.article;

import com.snews.server.dto.ArticleDto;
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
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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
            pictureLink = this.fileService.saveFile(file);
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

        chars.put('??', "a");
        chars.put('??', "b");
        chars.put('??', "v");
        chars.put('??', "g");
        chars.put('??', "d");
        chars.put('??', "e");
        chars.put('??', "zh");
        chars.put('??', "z");
        chars.put('??', "i");
        chars.put('??', "y");
        chars.put('??', "k");
        chars.put('??', "l");
        chars.put('??', "m");
        chars.put('??', "n");
        chars.put('??', "o");
        chars.put('??', "p");
        chars.put('??', "r");
        chars.put('??', "s");
        chars.put('??', "t");
        chars.put('??', "u");
        chars.put('??', "f");
        chars.put('??', "h");
        chars.put('??', "ts");
        chars.put('??', "ch");
        chars.put('??', "sh");
        chars.put('??', "sht");
        chars.put('??', "a");
        chars.put('??', "y");
        chars.put('??', "yu");
        chars.put('??', "ya");
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
}
