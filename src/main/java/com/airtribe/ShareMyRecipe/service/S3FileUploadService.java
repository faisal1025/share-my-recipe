package com.airtribe.ShareMyRecipe.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.io.IOException;
import java.util.UUID;

@Profile("!test")
@Service
public class S3FileUploadService implements FileStorageService {

    @Value("${cloud-aws-s3-bucket}")
    private String s3Bucket;

    @Autowired
    private S3Client s3Client;

    @Override
    public String uploadFile(MultipartFile file, String prefix) throws IOException {
        String originalName = file.getOriginalFilename();
        String extension = "";
        int idx = originalName.lastIndexOf(".");
        if(idx >= 0 && idx < originalName.length()-1){
            extension = originalName.substring(idx);
        }
        originalName.replaceAll("\\s+", "_");
        String key = "recipes/" + prefix + "/" + UUID.randomUUID().toString()+"_"+originalName;

        PutObjectResponse result = s3Client.putObject(
                PutObjectRequest.builder()
                        .bucket(s3Bucket)
                        .key(key)
                        .contentType(file.getContentType())
                        .build(),
                RequestBody.fromInputStream(file.getInputStream(), file.getSize())
        );

        return s3Client.utilities()
                .getUrl(GetUrlRequest.builder().bucket(s3Bucket).key(key).build())
                .toString();
    }

    @Override
    public ResponseInputStream<?> downloadFile(String key) {
        return s3Client.getObject(GetObjectRequest.builder()
                .bucket(s3Bucket)
                .key(key)
                .build()
        );
    }
}


