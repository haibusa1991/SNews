package com.snews.server.services.file;

import java.io.File;
import java.io.IOException;

public interface FileService {

    String savePictureToDisk(byte[] file) throws IOException;

    void generateThumbnail(byte[] file, String pictureLink) throws IOException;

    byte[] getPictureFromDisk(String image);

    byte[] getThumbnailFromDisk(String image);

    byte[] getAvatarFromDisk(String avatar);
}
