package com.snews.server.services.file;

import net.coobird.thumbnailator.Thumbnails;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

import static com.snews.server.configuration.Constants.*;

@Service
public class FileServiceImpl implements FileService {
    private byte[] configurationFileCache = null;


    @Override
    public String savePictureToDisk(byte[] file) {
        return saveToDisk(file, ARTICLE_IMAGES_FILEPATH);
    }

    private String getCurrentPath() {
        String filepath = this.getClass().getProtectionDomain().getCodeSource().getLocation().toString();
        StringBuilder sb = new StringBuilder(filepath.substring(filepath.indexOf(":/") + 2));

        while (sb.charAt(sb.length() - 1) != '/') {
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }

    private String getRootPath() {
        StringBuilder sb = new StringBuilder(getCurrentPath());

        for (int i = 0; i < 3; i++) {
            sb.deleteCharAt(sb.length() - 1);
            while (sb.charAt(sb.length() - 1) != '/') {
                sb.deleteCharAt(sb.length() - 1);
            }
        }
        return sb.toString();
    }

    @Override
//    todo return some value to confirm persistence
    public void generateThumbnail(byte[] file, String pictureName) throws IOException {
        ByteArrayOutputStream fileOutputStream = new ByteArrayOutputStream();

        Thumbnails.of(new ByteArrayInputStream(file))
                .size(400, 240)
                .keepAspectRatio(true)
                .outputQuality(0.8)
                .toOutputStream(fileOutputStream);

        try {
            Path storagePath = Path.of(getRootPath() + ARTICLE_THUMBNAILS_FILEPATH);
            Path filepath = Path.of(storagePath + "/" + pictureName + "_thumb");
            Files.createDirectories(storagePath);
            Files.createFile(filepath);
            Files.write(filepath, fileOutputStream.toByteArray());
        } catch (Exception ignored) {
        }
    }

    @Override
    public byte[] getArticleImageFromDisk(String image) {
        try {
            return Files.readAllBytes(Path.of(getRootPath() + ARTICLE_IMAGES_FILEPATH + image));
        } catch (IOException e) {
            return new byte[0];
        }

    }

    @Override
    public byte[] getArticleThumbnailFromDisk(String image) {
        try {
            return Files.readAllBytes(Path.of(getRootPath() + ARTICLE_THUMBNAILS_FILEPATH + "/" + image + "_thumb"));
        } catch (IOException e) {
            return new byte[0];
        }
    }

    @Override
    public byte[] getAvatarFromDisk(String avatar) {
        try {
            return Files.readAllBytes(Path.of(getRootPath() + AVATAR_FILEPATH + avatar));
        } catch (IOException e) {
            return new byte[0];
        }
    }

    @Override
    public String saveAvatarToDisk(byte[] file) throws IOException {
        ByteArrayOutputStream fileOutputStream = new ByteArrayOutputStream();

        Thumbnails.of(new ByteArrayInputStream(file))
                .size(100, 100)
                .keepAspectRatio(true)
                .outputQuality(0.8)
                .toOutputStream(fileOutputStream);

        return saveToDisk(fileOutputStream.toByteArray(), AVATAR_FILEPATH);
    }

    private String saveToDisk(byte[] file, String path) {
        String fileName = UUID.randomUUID().toString();
        try {
            Path storagePath = Path.of(getRootPath() + path);
            Path filepath = Path.of(storagePath + "/" + fileName);
            Files.createDirectories(storagePath);
            Files.createFile(filepath);
            Files.write(filepath, file);
            return fileName;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public byte[] readConfigurationFile() throws IOException {
        if (this.configurationFileCache != null) {
            return this.configurationFileCache;
        }

        try {
            this.configurationFileCache = Files.readAllBytes(Path.of(getRootPath() + CONFIGURATION_FILEPATH + CONFIGURATION_FILENAME));
            return this.configurationFileCache;
        } catch (Exception e) {
            Path storagePath = Path.of(getRootPath() + CONFIGURATION_FILEPATH);
            Path filepath = Path.of(storagePath + "/" + CONFIGURATION_FILENAME);
            Files.createDirectories(storagePath);
            Files.createFile(filepath);
            Files.write(filepath, new byte[0]);
            return new byte[0];
        }
    }

    @Override
    public void writeConfigurationFile(byte[] configuration) throws IOException {
        Path storagePath = Path.of(getRootPath() + CONFIGURATION_FILEPATH);
        Path filepath = Path.of(storagePath + "/" + CONFIGURATION_FILENAME);
        this.configurationFileCache = null;
        Files.write(filepath, configuration);
    }
}
