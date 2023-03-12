package com.snews.server.services.article;

import com.snews.server.dto.NewArticleDto;
import com.snews.server.exceptions.InternalServerErrorException;

import java.io.IOException;

public interface ArticleService {


    String save(NewArticleDto dto) throws IOException, InternalServerErrorException;
}
