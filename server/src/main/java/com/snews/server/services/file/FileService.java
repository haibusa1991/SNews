package com.snews.server.services.file;

import java.io.IOException;

public interface FileService {

    String savePictureToDisk(byte[] file) throws IOException;

    void generateThumbnail(byte[] file, String pictureLink) throws IOException;

    byte[] getArticleImageFromDisk(String image);

    byte[] getArticleThumbnailFromDisk(String image);

    byte[] getAvatarFromDisk(String avatar);

    String saveAvatarToDisk(byte[] file) throws IOException;

    byte[] readConfigurationFile() throws IOException;

    void writeConfigurationFile(byte[] configuration) throws IOException;
}
