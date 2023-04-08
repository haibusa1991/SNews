package com.snews.server.services.image;

import com.snews.server.entities.ImageEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface ImageService {

    ImageEntity saveImage(byte[] image);

    ImageEntity saveImage(MultipartFile image);

    byte[] getImage(UUID id);

    byte[] getImage(String id);
}
