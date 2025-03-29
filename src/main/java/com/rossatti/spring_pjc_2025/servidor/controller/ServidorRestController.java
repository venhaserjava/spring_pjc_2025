package com.rossatti.spring_pjc_2025.servidor.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rossatti.spring_pjc_2025.commons.routes.ApiRoutes;
//import com.rossatti.spring_pjc_2025.pessoa.entities.Pessoa;
import com.rossatti.spring_pjc_2025.pessoa.services.PessoaService;
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


//    @PostMapping(consumes = {"multipart/form-data"})
    public ResponseEntity<String> cadastrarServidor(
        @RequestPart("pessoa") String pessoaJson,  // JSON vem como String
        @RequestPart("foto") MultipartFile foto) {

        try {
            // Converter JSON recebido para o DTO ServidorRequest
            ServidorRequest servidor = objectMapper.readValue(pessoaJson, ServidorRequest.class);

            // Debug: Exibir os dados convertidos
            System.out.println("Nome: " + servidor.getNome());
            System.out.println("Endereços: " + servidor.getEnderecos().size());
            System.out.println("Foto recebida: " + foto.getOriginalFilename());

            return ResponseEntity.ok("Servidor cadastrado com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erro ao processar requisição: " + e.getMessage());
        }
    }

   //@PostMapping(path = "/servidores",consumes = {"multipart/form-data"})
//   @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
   @PostMapping(consumes = {"multipart/form-data"})
   public ResponseEntity<ServidorResponse> create(
            @RequestPart("pessoa") String pessoaJson,
            @RequestPart("foto") MultipartFile foto) throws IOException 
            
   {
      ServidorRequest servidor = objectMapper.readValue(pessoaJson, ServidorRequest.class);
//      System.out.println("JSON recebido: " + servidor);
//      System.out.println("Foto recebida: " + foto.getOriginalFilename());
//      ServidorResponse novaPessoa = new ServidorResponse();
      ServidorResponse novaPessoa = servidorService.create(servidor);
//      pessoaFotoService.salvarFoto(novaPessoa, foto);
      // EntityModel<PessoaDTO> resource = EntityModel.of(novaPessoa);
      // resource.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PessoaController.class)
      //            .criarPessoa(pessoaDTO, foto)).withSelfRel());
      //  return ResponseEntity.status(HttpStatus.CREATED).body(resource);
      //return ResponseEntity.ok().build();
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
            
            //String objectName = hash + ".jpg";
            // logger.info("Gerando link para objeto: " + objectName);
            // logger.info("Bucket: " + bucketName);            

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
