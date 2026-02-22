package com.airtribe.ShareMyRecipe.service;

import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.ResponseInputStream;

import java.io.IOException;

public interface FileStorageService {
    String uploadFile(MultipartFile file, String prefix) throws IOException;
    ResponseInputStream<?> downloadFile(String key);
}
