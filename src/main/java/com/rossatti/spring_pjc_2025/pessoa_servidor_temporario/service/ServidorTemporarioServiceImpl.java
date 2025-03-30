package com.rossatti.spring_pjc_2025.pessoa_servidor_temporario.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import static org.springframework.http.HttpStatus.*;

import com.rossatti.spring_pjc_2025.pessoa.entities.Pessoa;
import com.rossatti.spring_pjc_2025.pessoa.repositories.PessoaRepository;
import com.rossatti.spring_pjc_2025.pessoa_servidor_efetivo.repositories.ServidorEfetivoRepository;
import com.rossatti.spring_pjc_2025.pessoa_servidor_temporario.dtos.request.ServidorTemporarioRequest;
import com.rossatti.spring_pjc_2025.pessoa_servidor_temporario.entities.ServidorTemporario;
import com.rossatti.spring_pjc_2025.pessoa_servidor_temporario.repositories.ServidorTemporarioRepository;

@Service
public class ServidorTemporarioServiceImpl implements ServidorTemporarioService {

    private final PessoaRepository pessoaRepository;
    private final ServidorTemporarioRepository servidorTemporarioRepository;
    private final ServidorEfetivoRepository servidorEfetivoRepository;

    public ServidorTemporarioServiceImpl(
        PessoaRepository pessoaRepository,
        ServidorTemporarioRepository servidorTemporarioRepository,
        ServidorEfetivoRepository servidorEfetivoRepository
    ) {
        this.pessoaRepository = pessoaRepository;
        this.servidorTemporarioRepository = servidorTemporarioRepository;
        this.servidorEfetivoRepository = servidorEfetivoRepository;
    }

    @Transactional
    public void cadastrarServidorTemporario(ServidorTemporarioRequest dto) {

        List<String> erros = new ArrayList<>();        
        
        //  Validação da Pessoa
        Optional<Pessoa> pessoaOpt = pessoaRepository.findById(dto.getPessoaId());
        if (pessoaOpt.isEmpty()) {
            erros.add("Pessoa não encontrada.");
        }

        // verifica se a pessoa tem um registro como servidor Temporario em "Aberto" 
        if ( !servidorTemporarioRepository.existsByPessoaIdAndDataDemissaoIsNull(dto.getPessoaId())) {

           //verifica a data de Admissao 
           if(!getValidaAdmissao(dto)){
                erros.add("A Data de ADMISSAO deve ser posterior ao 18º aniversário da pessoa.");            
           }
           // Verifica a data de Demissao
           if (!getValidaDemissao(dto)) {
                erros.add("A Data de DEMISSÃO deve ser posterior a data de Admissao.");                 
           }
           
        } else {
            erros.add("Esta Pessoa já está cadastrada não pode ser Incluida novamente!");
        }
        // Verificando se a pessoa é Servidor Efetivo
        if (servidorEfetivoRepository.existsByPessoaId(dto.getPessoaId())) {
            erros.add("Este servidor é efetivo, não pode ser cadastrado como temporario.");            
        }

        // 🔹 Se houver erros, lançar exceção com JSON no formato esperado
        if (!erros.isEmpty()) {
            throw new ResponseStatusException(BAD_REQUEST, criarMensagemErro(erros));
        } else {
            // 🔹 Criar e salvar Servidor Temporário
            ServidorTemporario servidorTemporario = new ServidorTemporario();
            servidorTemporario.setPessoa(pessoaOpt.get());
            servidorTemporario.setPessoaId(dto.getPessoaId());        
            servidorTemporario.setDataAdmissao(dto.getDataAdmissao());
            servidorTemporarioRepository.save(servidorTemporario);
        }
    }


    @Transactional
    public void update(Long pessoaId,ServidorTemporarioRequest dto){
        List<String> erros = new ArrayList<>();        
        
        //  Verificando se a pessoa é ServidorTemporario
        if (!servidorTemporarioRepository.existsByPessoaIdAndDataDemissaoIsNull(pessoaId)) {                   
            erros.add("Este servidor não tem registro de Servidor Temporario em vigencia!");
        } else {
                //verifica a data de Admissao 
            if(!getValidaAdmissao(dto)){
                erros.add("A Data de ADMISSAO deve ser posterior ao 18º aniversário da pessoa.");            
            }
            // Verifica a data de Demissao
            if (!getValidaDemissao(dto)) {
                erros.add("A Data de DEMISSÃO deve ser posterior a data de Admissao.");                 
            }
        }
        // Verificando se a pessoa é Servidor Efetivo
        if (servidorEfetivoRepository.existsByPessoaId(pessoaId)) {
            erros.add("Este servidor é efetivo, não pode ser alterado como temporario.");            
        }

        //  Se houver erros, lançar exceção com JSON no formato esperado
        if (!erros.isEmpty()) {
            throw new ResponseStatusException(BAD_REQUEST, criarMensagemErro(erros));           
        } else {
            // Gravando a atualização do Servidor Temporario
            ServidorTemporario servidorTemporario = new ServidorTemporario();
            servidorTemporario.setPessoaId(pessoaId);        
            servidorTemporario.setDataAdmissao(dto.getDataAdmissao());
            servidorTemporario.setDataAdmissao(dto.getDataDemissao());
            servidorTemporarioRepository.save(servidorTemporario);
        }
    }
    private String criarMensagemErro(List<String> erros) {
        if (erros.size() == 1) {
            return "{\"erro\": \"" + erros.get(0) + "\"}";
        } else {
            return "{\"erros\": " + erros.toString() + "}";
        }
    }
    private boolean getValidaAdmissao(ServidorTemporarioRequest dto) {

        Optional<Pessoa> pessoaOpt = pessoaRepository.findById(dto.getPessoaId());
        // verifica se a admissao é nula
        if (dto.getDataAdmissao() == null) {
            return false;                
        }
            // verifica se a pessoa era maior de idade no ato da admissão           
        LocalDate dataNascimento = pessoaOpt.map(Pessoa::getDataNascimento).orElse(null);
        if (dataNascimento != null && dataNascimento.plusYears(18).isAfter(dto.getDataAdmissao())) {
            return false;
        }
        return true;
    }
    private boolean getValidaDemissao(ServidorTemporarioRequest dto){

        if (dto.getDataDemissao()==null) {
            return true;
        }
        if ( dto.getDataAdmissao().isAfter(dto.getDataDemissao()) ) {
            return false;            
        }
        return true;
    }

}
