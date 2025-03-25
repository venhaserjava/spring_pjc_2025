package com.rossatti.spring_pjc_2025.commons.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.minio.MinioClient;

@Configuration
public class MinioClientConfig {

    @Bean
    MinioClient minioClient() {
        return MinioClient.builder()
            .endpoint("http://localhost:9000")
            .credentials("ROOTUSER", "mudarSenha@123")
            .build();
    }

}
