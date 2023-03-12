package com.snews.server.services.file;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public interface FileService {

    String saveFile(byte[] file) throws IOException;
}
