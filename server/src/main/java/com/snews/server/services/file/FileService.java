package com.snews.server.services.file;

import java.io.IOException;

public interface FileService {

    String savePictureToDisk(byte[] file) throws IOException;

    void generateThumbnail(byte[] file, String pictureLink) throws IOException;
}
