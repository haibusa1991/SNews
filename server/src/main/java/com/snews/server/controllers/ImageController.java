package com.snews.server.controllers;

import com.snews.server.services.file.FileService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
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
    public ResponseEntity<Resource> getImage(@PathVariable String image) {
        ByteArrayResource resource = new ByteArrayResource(this.fileService.getPictureFromDisk(image));

        return ResponseEntity.ok()
                .contentLength(resource.contentLength())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }

    @GetMapping("thumbnails/{thumbnail}")
    public ResponseEntity<Resource> getThumbnail(@PathVariable String thumbnail) {
        ByteArrayResource resource = new ByteArrayResource(this.fileService.getThumbnailFromDisk(thumbnail));

        return ResponseEntity.ok()
                .contentLength(resource.contentLength())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }
}
