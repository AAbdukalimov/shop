package com.example.shop.service.file;

import com.example.shop.dto.UploadResult;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Path;
import java.util.List;

public interface FileService {
    UploadResult upload(MultipartFile file);
    List<String> loadAll();

    Path load(String filename);

    Resource loadAsResource(String filename);

    void delete(String key);
}
