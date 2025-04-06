package com.rossatti.spring_pjc_2025.commons.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.minio.MinioClient;

@Configuration
public class MinioClientConfig {

    private final MinioProperties minioProperties;
    
    public MinioClientConfig(MinioProperties minioProperties) {
        this.minioProperties = minioProperties;
    }

    @Bean
    MinioClient minioClient() {
        return MinioClient.builder()
        .endpoint(minioProperties.getUrl())
        .credentials(minioProperties.getAccessKey(),minioProperties.getSecretKey())
            .build();
    }
}
