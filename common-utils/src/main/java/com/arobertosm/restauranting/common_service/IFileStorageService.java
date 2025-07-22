package com.arobertosm.restauranting.common_service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface IFileStorageService {
    String store(MultipartFile file, String subDirectory, String baseFilename);
    Resource loadFileAsResource(String fileName);
}
