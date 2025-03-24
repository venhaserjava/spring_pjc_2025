package com.rossatti.spring_pjc_2025.commons.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.minio.MinioClient;

@Configuration
class MinioConfig {
    @Bean
    public MinioClient minioClient() {
        return MinioClient.builder()
                .endpoint("http://localhost:9001")  // Altere conforme necessário
                .credentials("ROOTUSER", "mudarSenha@123")  // Altere conforme necessário
                .build();
    }
}
