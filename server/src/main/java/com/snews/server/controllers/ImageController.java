package com.snews.server.controllers;

import com.snews.server.services.image.ImageService;
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

    private final ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @GetMapping("images/{image}")
    public ResponseEntity<byte[]> getArticleImage(@PathVariable String image) {
        return resourceResponse(this.imageService.getImage(image));
    }

    private ResponseEntity<byte[]> resourceResponse(byte[] resource) {
        ByteArrayResource content = new ByteArrayResource(resource);

        return ResponseEntity.ok()
                .contentLength(content.contentLength())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }
}
