package com.rossatti.spring_pjc_2025.endereco.validators;

import org.springframework.stereotype.Component;

import com.rossatti.spring_pjc_2025.cidade.entitys.Cidade;
import com.rossatti.spring_pjc_2025.cidade.exceptions.CidadeNotFoundException;
import com.rossatti.spring_pjc_2025.cidade.repositories.CidadeRepository;
import com.rossatti.spring_pjc_2025.endereco.dtos.request.EnderecoRequest;
import com.rossatti.spring_pjc_2025.endereco.repositories.EnderecoRepository;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UniqueEnderecoValidator  implements ConstraintValidator<UniqueEndereco,EnderecoRequest> {

    private final CidadeRepository cidadeRepository;
    private final EnderecoRepository repository;
    
    //@Autowired
    // public UniqueEnderecoValidator(EnderecoRepository repository,CidadeRepository cidadeRepository){
    //     this.repository = repository;
    //     this.cidadeRepository = cidadeRepository;
    // }
    @Override
    public void initialize(UniqueEndereco constraintAnnotation) {
    }    

    @Override
    public boolean isValid(EnderecoRequest request,ConstraintValidatorContext context){
        if(request == null){
            return true;
        }
        if (!cidadeRepository.existsById(request.getCidadeId())) {
            return true;            
        }
        Cidade cidade = cidadeRepository.findById(request.getCidadeId())
                            .orElseThrow(CidadeNotFoundException::new);
        System.out.println(cidade.toString());                
        return !repository.existsByLogradouroAndNumeroAndBairroAndCidade(
            request.getLogradouro(),
            request.getNumero(),
            request.getBairro(),
            cidade);
    }

}



// import com.rossatti.spring_pjc_2025.cidade.dtos.request.CidadeRequest;
// import com.rossatti.spring_pjc_2025.cidade.repositories.CidadeRepository;
// import com.rossatti.spring_pjc_2025.endereco.dtos.request.EnderecoRequest;
// import com.rossatti.spring_pjc_2025.endereco.repositories.EnderecoRepository;

// import jakarta.validation.ConstraintValidator;
// import jakarta.validation.ConstraintValidatorContext;
// import lombok.RequiredArgsConstructor;

// @Component
// @RequiredArgsConstructor
// public class UniqueCidadeValidator implements ConstraintValidator<UniqueCidade,CidadeRequest> {

//     @Autowired
//     private CidadeRepository cidadeRepository;

//     @Override
//     public boolean isValid(CidadeRequest request, ConstraintValidatorContext context) {
//         if (request == null) {
//             return true; // Não valida objetos nulos
//         }
//         return !cidadeRepository.existsByNomeAndUf(request.getNome(), request.getUf());
//     }
// }
