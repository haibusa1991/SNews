package com.snews.server.services.file;

import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.FileAttribute;
import java.util.UUID;

import static com.snews.server.configuration.Constants.ARTICLE_PICTURES_FILEPATH;

@Service
public class FileServiceImpl implements FileService {

    @Override
    public String saveFile(byte[] file) {
        String fileName = UUID.randomUUID().toString();
        try {
            Path storagePath = Path.of(getCurrentPath() + ARTICLE_PICTURES_FILEPATH);
            Path filepath = Path.of(storagePath + "/" + fileName);
            Files.createDirectories(storagePath);
            Files.createFile(filepath);
            Files.write(filepath, file);
            return fileName;
        } catch (Exception e) {
            return null;
        }
    }


    private String getCurrentPath() {
        String filepath = this.getClass().getProtectionDomain().getCodeSource().getLocation().toString();
        StringBuilder sb = new StringBuilder(filepath.substring(filepath.indexOf(":/") + 2));

        while (sb.charAt(sb.length() - 1) != '/') {
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }
}
