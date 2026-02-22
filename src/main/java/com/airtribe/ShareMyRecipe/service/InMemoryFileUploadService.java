package com.airtribe.ShareMyRecipe.service;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.ResponseInputStream;

import java.io.IOException;

@Service
@Profile("test")
public class InMemoryFileUploadService implements FileStorageService{

    @Override
    public String uploadFile(MultipartFile file, String prefix) throws IOException {
        return "";
    }

    @Override
    public ResponseInputStream<?> downloadFile(String key) {
        return null;
    }
}
