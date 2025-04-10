package com.rossatti.spring_pjc_2025.pessoa_foto.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;


import com.rossatti.spring_pjc_2025.pessoa_foto.services.PessoaFotoService;

import io.minio.BucketExistsArgs;
import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.http.Method;

@RestController
@RequestMapping("/api/foto_pessoa")
public class PessoaFotoRestController {
    @Autowired
    private PessoaFotoService pessoaFotoService;

    @Autowired
    private MinioClient minioClient;

    private final String bucketName = "fotos";

//    private static final Logger logger = LoggerFactory.getLogger(PessoaFotoRestController.class);


    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadFoto(@RequestParam("file") MultipartFile file,
                                         @RequestParam("pessoaId") Long pessoaId) {
        try {
            String hash = pessoaFotoService.generateHash(file);
            String fileName = hash + ".png";


            if (!minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build())) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
            }

            minioClient.putObject(
                PutObjectArgs.builder()
                    .bucket(bucketName)
                    .object(fileName)
                    .stream(file.getInputStream(), file.getSize(), -1)
                    .contentType(file.getContentType())
                    .build()
            );
            pessoaFotoService.saveFoto(pessoaId, hash);
//            return ResponseEntity.ok("Foto salva com sucesso. Hash: " + hash);
            return ResponseEntity.ok("Foto salva com sucesso. Hash: " + obterLinkTemporario(hash) );
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erro ao enviar foto: " + e.getMessage());
        }
    }

    @GetMapping("/listar/{pessoaId}")
    public ResponseEntity<List<String>> listarFotos(@PathVariable Long pessoaId) {
        List<String> fotos = pessoaFotoService.listarFotosPorPessoa(pessoaId);
        return ResponseEntity.ok(fotos);
    }

    @GetMapping("/link/{hash}")
    public ResponseEntity<String> obterLinkTemporario(@PathVariable String hash) {
        try {            

            String url = minioClient.getPresignedObjectUrl(
                GetPresignedObjectUrlArgs.builder()
                    .method(Method.GET)
                    .bucket(bucketName)
                    .object(hash + ".jpg")
                    .expiry(300)
                    .build()
            );

            return ResponseEntity.ok(url);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erro ao gerar link temporário: " + e.getMessage());
        }
    }

/*    
    @GetMapping("/link/{hash}")
    public ResponseEntity<String> obterLinkTemporario(@PathVariable String hash) {
        try {
            String url = minioClient.getPresignedObjectUrl(
                GetPresignedObjectUrlArgs.builder()
                    .method(Method.GET)
                    .bucket(bucketName)
                    .object(hash + ".jpg")
                    .expiry(5, TimeUnit.MINUTES)
                    .build()
            );
            return ResponseEntity.ok(url);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erro ao gerar link temporário: " + e.getMessage());
        }
    }

    @DeleteMapping("/remover/{hash}")
    public ResponseEntity<?> removerFoto(@PathVariable String hash) {
        try {
            minioClient.removeObject(
                io.minio.RemoveObjectArgs.builder()
                    .bucket(bucketName)
                    .object(hash + ".jpg")
                    .build()
            );
            pessoaFotoService.removerFoto(hash);
            return ResponseEntity.ok("Foto removida com sucesso");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erro ao remover foto: " + e.getMessage());
        }
    }
*/        

}
