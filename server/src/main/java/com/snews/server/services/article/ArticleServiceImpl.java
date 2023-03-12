package com.snews.server.services.article;

import com.snews.server.dto.NewArticleDto;
import com.snews.server.exceptions.InternalServerErrorException;
import com.snews.server.services.file.FileService;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class ArticleServiceImpl implements ArticleService {

    private final FileService fileService;

    public ArticleServiceImpl(FileService fileService) {
        this.fileService = fileService;
    }

    @Override
    public String save(NewArticleDto dto) throws InternalServerErrorException {

        try {
            byte[] file = dto.getPictureFile().getBytes();
            this.fileService.saveFile(file);
        } catch (Exception e) {
            throw new InternalServerErrorException("Unable to save image");
        }

        return "link-to-new-article";
    }
}
