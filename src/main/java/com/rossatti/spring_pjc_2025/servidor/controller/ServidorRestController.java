package com.rossatti.spring_pjc_2025.servidor.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rossatti.spring_pjc_2025.pessoa_foto.services.PessoaFotoService;
import com.rossatti.spring_pjc_2025.servidor.dtos.request.ServidorRequest;
import com.rossatti.spring_pjc_2025.servidor.dtos.response.ServidorResponse;
import com.rossatti.spring_pjc_2025.servidor.services.ServidorService;

import io.minio.BucketExistsArgs;
import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.http.Method;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/servidores")
public class ServidorRestController {

   //private final PessoaService service;
   private final ServidorService servidorService;
   private final PessoaFotoService pessoaFotoService;
   private final ObjectMapper objectMapper; //= new ObjectMapper(); // Objeto para converter JSON em DTO

    @Autowired
    private MinioClient minioClient;

    private final String bucketName = "fotos";

   @PostMapping(consumes = {"multipart/form-data"})
   public ResponseEntity<ServidorResponse> create(
            @RequestPart("pessoa") String pessoaJson,
            @RequestPart("foto") MultipartFile foto) throws IOException 
            
   {
      ServidorRequest servidor = objectMapper.readValue(pessoaJson, ServidorRequest.class);
      ServidorResponse novaPessoa = servidorService.create(servidor);
   //---------------------------
   // FOR CODE_REVIEW
   //---------------------------
        try {
            String hash = pessoaFotoService.generateHash(foto);
            String fileName = hash + ".png";


            if (!minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build())) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
            }

            minioClient.putObject(
                PutObjectArgs.builder()
                    .bucket(bucketName)
                    .object(fileName)
                    .stream(foto.getInputStream(), foto.getSize(), -1)
                    .contentType(foto.getContentType())
                    .build()
            );
            pessoaFotoService.saveFoto(novaPessoa.getId(), hash);
            novaPessoa.setFoto(obterLinkTemporario(hash));
//            return ResponseEntity.ok("Foto salva com sucesso. Hash: " + hash);
            return ResponseEntity.ok(novaPessoa)   ;
//            return ResponseEntity.ok("Foto salva com sucesso. Hash: " + obterLinkTemporario(hash) );

        } catch (Exception e) {
//            return ResponseEntity.status(500).body("Erro ao enviar foto: " + e.getMessage());
            return ResponseEntity.status(500).body(new ServidorResponse());
        }
   }
        
   //---------------------------
   // FOR CODE_REVIEW
   //---------------------------    
    public String obterLinkTemporario(@PathVariable String hash) {
        try {            

            String url = minioClient.getPresignedObjectUrl(
                GetPresignedObjectUrlArgs.builder()
                    .method(Method.GET)
                    .bucket(bucketName)
                    .object(hash + ".jpg")
                    .expiry(300)
                    .build()
            );
            return url;
        } catch (Exception e) {
            return "Erro ao gerar link temporário: ";
        }
      }
}
