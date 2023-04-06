package com.snews.server.controllers;

import com.snews.server.services.file.FileService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller()
@RequestMapping(path = "/")
public class ImageController {

    private final FileService fileService;

    public ImageController(FileService fileService) {
        this.fileService = fileService;
    }

    @GetMapping("images/{image}")
    public ResponseEntity<byte[]> getArticleImage(@PathVariable String image) {
        return resourceResponse(this.fileService.getArticleImageFromDisk(image));
    }

    @GetMapping("thumbnails/{thumbnail}")
    public ResponseEntity<byte[]> getArticleThumbnail(@PathVariable String thumbnail) {
        return resourceResponse(this.fileService.getArticleThumbnailFromDisk(thumbnail));
    }

    @GetMapping("avatars/{avatar}")
    public ResponseEntity<byte[]> getAvatar(@PathVariable String avatar) {
        return resourceResponse(this.fileService.getAvatarFromDisk(avatar));
    }

    private ResponseEntity<byte[]> resourceResponse(byte[] resource) {
        ByteArrayResource content = new ByteArrayResource(resource);

        return ResponseEntity.ok()
                .contentLength(content.contentLength())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }
}
