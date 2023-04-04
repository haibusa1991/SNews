package com.snews.server.services.query;

import com.snews.server.dto.ArticleOverviewDto;
import com.snews.server.dto.UserDto;
import com.snews.server.entities.ArticleEntity;
import com.snews.server.entities.UserEntity;
import com.snews.server.repositories.ArticleRepository;
import com.snews.server.repositories.UserRepository;
import com.snews.server.utils.Utils;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class QueryServiceImpl implements QueryService {

    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public QueryServiceImpl(ArticleRepository articleRepository, UserRepository userRepository, ModelMapper modelMapper) {
        this.articleRepository = articleRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public ArticleOverviewDto[] articleSearch(String query) {

        if (query.length() < 3) {
            return new ArticleOverviewDto[0];
        }

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.M.yyyy H:m:s");
            LocalDateTime date = LocalDateTime.parse(query.trim() + " 00:00:00", formatter);
            return dateSearch(date);
        } catch (Exception ignored) {
        }

        try {
            return Utils.convertToDtoArray(
                    Arrays.stream(query.split(" "))
                            .map(this.articleRepository::getArticleHeadingByKeywordPartialMatch)
                            .flatMap(Collection::stream)
                            .collect(Collectors.toSet())
                            .stream()
                            .map(this.articleRepository::getArticleEntityByHeading)
                            .map(article -> rateArticle(article, query))
                            .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                            .map(Map.Entry::getKey)
                            .toList()
            );
        } catch (Exception e) {
            return new ArticleOverviewDto[0];
        }
    }

    private ArticleOverviewDto[] dateSearch(LocalDateTime date) {
        List<ArticleEntity> all = this.articleRepository.findAllByPublishedBetweenOrderByPublishedDesc(date, date.plusDays(1));
        return Utils.convertToDtoArray(all);
    }

    private Map.Entry<ArticleEntity, Integer> rateArticle(ArticleEntity article, String query) {
        String[] headingWords = article.getHeading().split(" ");
        String[] keywords = query.split(" ");

        int relevanceRating = 0;

        for (String headingWord : headingWords) {
            for (String keyword : keywords) {
                if (headingWord.equalsIgnoreCase(keyword)) {
                    relevanceRating++;
                }
            }
        }
        return Map.entry(article, relevanceRating);
    }

    @Override
    public UserDto[] userSearch(String username) {
        List<UserEntity> matches = this.userRepository.getUserEntitiesByUsernameContainingOrderByUsernameAsc(username);

        return matches.stream().map(user -> modelMapper.map(user, UserDto.class)).toArray(UserDto[]::new);
    }
}

