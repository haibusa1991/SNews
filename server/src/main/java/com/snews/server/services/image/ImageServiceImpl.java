package com.snews.server.services.image;

import com.snews.server.entities.ImageEntity;
import com.snews.server.repositories.ImageRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Service
public class ImageServiceImpl implements ImageService {

    private final ImageRepository imageRepository;

    public ImageServiceImpl(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    @Override
    public ImageEntity saveImage(byte[] image) {
        ImageEntity imageEntity = new ImageEntity();
        imageEntity.setData(image);
        return this.imageRepository.save(imageEntity);
    }

    @Override
    public ImageEntity saveImage(MultipartFile image) {
        byte[] bytes;
        try {
            bytes = image.getBytes();
        } catch (Exception e) {
            bytes = new byte[0];
        }

        return this.saveImage(bytes);
    }

    @Override
    public byte[] getImage(UUID id) {

        return this.imageRepository.getImageEntityById(id).getData();
    }

    @Override
    public byte[] getImage(String id) {
        try {
            return this.getImage(UUID.fromString(id));
        } catch (Exception e) {
            return new byte[0];
        }
    }
}
