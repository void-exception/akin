package com.example.demo.Model;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class FileStorageService {

    @Value("${cloud.yandex.s3.endpoint}")
    private String endpoint;

    @Value("${cloud.yandex.s3.access-key}")
    private String accessKey;

    @Value("${cloud.yandex.s3.secret-key}")
    private String secretKey;

    @Value("${cloud.yandex.s3.bucket-name}")
    private String bucketName;

    public String uploadFileToYandexCloud(MultipartFile file) throws IOException
    {
        S3Client s3 = S3Client.builder()
                .endpointOverride(URI.create(endpoint))
//                .region(Region.US_EAST_1)
                .region(Region.of("ru-central1"))
                .credentialsProvider(StaticCredentialsProvider.create(
                        AwsBasicCredentials.create(accessKey, secretKey)
                ))
                .build();

        String uniqueName = UUID.randomUUID() + "-" + file.getOriginalFilename();

        Path tempFile = Files.createTempFile("upload-", uniqueName);
        Files.copy(file.getInputStream(), tempFile, StandardCopyOption.REPLACE_EXISTING);

        try {
        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(uniqueName)
                .contentType(file.getContentType())
                .build();

        s3.putObject(putObjectRequest, tempFile);

        }
        catch (S3Exception e) {
            throw new RuntimeException("Error uploading a file to Object Storage: " + e.awsErrorDetails().errorMessage(), e);
        }
        finally {
            Files.deleteIfExists(tempFile);
        }

        return String.format("%s/%s/%s", endpoint, bucketName, uniqueName);

    }

}
