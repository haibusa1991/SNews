package com.snews.server.services.query;

import com.snews.server.dto.ArticleOverviewDto;
import com.snews.server.entities.ArticleEntity;
import com.snews.server.repositories.ArticleRepository;
import com.snews.server.utils.Utils;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class QueryServiceImpl implements QueryService {

    private final ArticleRepository articleRepository;
    private final ModelMapper modelMapper;

    public QueryServiceImpl(ArticleRepository articleRepository, ModelMapper modelMapper) {
        this.articleRepository = articleRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public ArticleOverviewDto[] articleSearch(String query) {

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.M.yyyy H:m:s");
            LocalDateTime date = LocalDateTime.parse(query.trim() + " 00:00:00", formatter);
            return dateSearch(date);
        } catch (Exception ignored) {
        }

        HashSet<String> anymatch = new HashSet<>();
        String[] keywords = query.split(" ");

//TODO get all headings that have any matching keyword
//    rate all the keywords by occurrence count
//    sort results by rating first, then by publish date
//    convert results to dtos
//    implement pagination if required


        Set<String> testRepo = new HashSet<>();
        for (String keyword : keywords) {
            List<String> headings = this.articleRepository.getAllHeadingsByKeywordMatch(keyword);
            testRepo.addAll(headings);
        }

        System.out.println();
        for (String keyword : keywords) {
            anymatch.addAll(
                    this.articleRepository
                            .findAllByHeadingContainingIgnoreCase(keyword)
                            .stream()
                            .map(ArticleEntity::getHeading)
                            .toList()
            );
        }

        HashMap<String, Integer> articlesWithRelevance = new HashMap<>();
        for (String article : anymatch) {
            int relevanceRating = 0;
            for (String keyword : keywords) {
                if (article.toLowerCase().contains(keyword.toLowerCase())) {
                    relevanceRating++;
                }
            }
            articlesWithRelevance.put(article, relevanceRating);
        }

//        articlesWithRelevance.entrySet().stream()

        Pageable pageable = PageRequest.of(10, 2);

        List<ArticleEntity> articles = this.articleRepository.findAllByOrderByPublishedDesc(pageable);

        return articles.stream().map(e -> this.modelMapper.map(e, ArticleOverviewDto.class)).toArray(ArticleOverviewDto[]::new);
    }

    private ArticleOverviewDto[] dateSearch(LocalDateTime date) {
        List<ArticleEntity> all = this.articleRepository.findAllByPublishedBetweenOrderByPublishedDesc(date, date.plusDays(1));
        return Utils.convertToDtoArray(all);
    }
}

