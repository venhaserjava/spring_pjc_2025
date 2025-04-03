package com.rossatti.spring_pjc_2025.pessoa_servidor_temporario.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import jakarta.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import static org.springframework.http.HttpStatus.*;

import com.rossatti.spring_pjc_2025.endereco.entities.Endereco;
import com.rossatti.spring_pjc_2025.pessoa.entities.Pessoa;
import com.rossatti.spring_pjc_2025.pessoa.repositories.PessoaRepository;
import com.rossatti.spring_pjc_2025.pessoa_servidor_efetivo.repositories.ServidorEfetivoRepository;
import com.rossatti.spring_pjc_2025.pessoa_servidor_temporario.dtos.request.ServidorTemporarioRequest;
import com.rossatti.spring_pjc_2025.pessoa_servidor_temporario.dtos.response.ServidorTemporarioDTO;
import com.rossatti.spring_pjc_2025.pessoa_servidor_temporario.dtos.response.ServidorTemporarioResponse;
import com.rossatti.spring_pjc_2025.pessoa_servidor_temporario.entities.ServidorTemporario;
import com.rossatti.spring_pjc_2025.pessoa_servidor_temporario.mappers.ServidorTemporarioMapper;
import com.rossatti.spring_pjc_2025.pessoa_servidor_temporario.repositories.ServidorTemporarioRepository;

@Service
public class ServidorTemporarioServiceImpl implements ServidorTemporarioService {

    private final PessoaRepository pessoaRepository;
    private final ServidorTemporarioRepository servidorTemporarioRepository;
    private final ServidorEfetivoRepository servidorEfetivoRepository;
    private final ServidorTemporarioMapper mapper;

    public ServidorTemporarioServiceImpl(
        PessoaRepository pessoaRepository,
        ServidorTemporarioRepository servidorTemporarioRepository,
        ServidorEfetivoRepository servidorEfetivoRepository,
        ServidorTemporarioMapper mapper
    ) {
        this.pessoaRepository = pessoaRepository;
        this.servidorTemporarioRepository = servidorTemporarioRepository;
        this.servidorEfetivoRepository = servidorEfetivoRepository;
        this.mapper = mapper;
    }
    
    public ServidorTemporarioResponse findByPessoaId(Long pessoaId){
        
        Optional<Pessoa> pessoaOpt = pessoaRepository.findById(pessoaId);
        Optional<ServidorTemporario> servTempOpt = servidorTemporarioRepository.findByPessoaIdAndDataDemissaoIsNull(pessoaId);
        if (pessoaOpt.isEmpty() || servTempOpt.isEmpty()) {
            return new ServidorTemporarioResponse();            
        }
        return mapper.toResponse(pessoaOpt.get(),servTempOpt.get());
    }

    public Page<ServidorTemporarioDTO> findAllServidoresTemporarios(String nome, Pageable pageable) {
        Page<ServidorTemporario> servidores;
        if (nome != null && !nome.isBlank()) {
            servidores = servidorTemporarioRepository.findAllByPessoaNomeContainingIgnoreCaseAndDataDemissaoIsNull(nome, pageable);
        } else {
            servidores = servidorTemporarioRepository.findAllByDataDemissaoIsNull(pageable);
        }
        return servidores.map(this::mapToDTO);
    } 

    @Transactional
    public void create(ServidorTemporarioRequest dto) {

        List<String> erros = new ArrayList<>();        
        Optional<Pessoa> pessoaOpt = pessoaRepository.findById(dto.getPessoaId());               
        
        //  Validação da Pessoa
        if (pessoaOpt.isEmpty()) {
            erros.add("Pessoa não encontrada.");
        }
        // Verificando se a pessoa é Servidor Efetivo
        if (servidorEfetivoRepository.existsByPessoaId(dto.getPessoaId())) {
            erros.add("Este servidor é efetivo, não pode ser cadastrado como temporario.");            
        } else {
            // verifica se a pessoa tem um registro como servidor Temporario em "Aberto" 
            if ( !servidorTemporarioRepository.existsByPessoaIdAndDataDemissaoIsNull(dto.getPessoaId())) {
                //verifica a data de Admissao 
                if(!getValidaAdmissao(pessoaOpt,dto)){
                        erros.add("A Data de ADMISSAO deve ser posterior ao 18º aniversário da pessoa.");            
                }
                // Verifica a data de Demissao
                if (!getValidaDemissao(dto)) {
                        erros.add("A Data de DEMISSÃO deve ser posterior a data de Admissao.");                 
                }            
            } else {
                erros.add("Esta Servidor já está cadastrado como Temporário e não pode ser Incluida novamente!");
            }
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
        Optional<Pessoa> pessoaOpt = pessoaRepository.findById(pessoaId);
        Optional<ServidorTemporario> servTempOpt = servidorTemporarioRepository.findByPessoaIdAndDataDemissaoIsNull(pessoaId);

        //  Validação da Pessoa
        if (pessoaOpt.isEmpty()) {
            erros.add("Pessoa não encontrada.");
        } else {
            // Verificando se a pessoa é Servidor Efetivo
            if (servidorEfetivoRepository.existsByPessoaId(pessoaId)) {
                erros.add("Este servidor é EFETIVO, não pode ser alterado como temporario.");            
            } else{
                // verficando se servidor temporario sem data de demissao, existe
                if(servTempOpt.isEmpty()){                        
                    erros.add("Este servidor não tem registro de Servidor Temporario em vigencia!");                                            
                } else {                                      
                    //verifica a data de Admissao 
                    if(!getValidaAdmissao(pessoaOpt,dto)){
                        erros.add("A Data de ADMISSAO deve ser posterior ao 18º aniversário da pessoa.");            
                    } else {
                        // Verifica a data de Demissao
                        if (!getValidaDemissao(dto)) {
                            erros.add("A Data de DEMISSÃO deve ser posterior a data de Admissao.");                 
                        }                    
                    }    
                }
            }
        }
        //  Se houver erros, lançar exceção com JSON no formato esperado
        if (!erros.isEmpty()) {
            throw new ResponseStatusException(BAD_REQUEST, criarMensagemErro(erros));           
        } else {
            // Gravando a atualização do Servidor Temporario
            ServidorTemporario servidorTemporario = new ServidorTemporario();
            servidorTemporario.setPessoaId(pessoaId);        
            servidorTemporario.setId(servTempOpt.get().getId());
            servidorTemporario.setDataAdmissao(dto.getDataAdmissao());
            servidorTemporario.setDataDemissao(dto.getDataDemissao());
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

    private boolean getValidaAdmissao(Optional<Pessoa> pessoaOpt,ServidorTemporarioRequest dto) {

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
    
    public ServidorTemporarioDTO mapToDTO(ServidorTemporario servidorTemporario) {
        Pessoa pessoa = servidorTemporario.getPessoa();
        
        // Verifica se a pessoa tem endereços antes de acessar
        Endereco endereco = (pessoa.getEnderecos() != null && !pessoa.getEnderecos().isEmpty()) 
            ? pessoa.getEnderecos().iterator().next() 
            : null;

        return new ServidorTemporarioDTO(
            pessoa.getId(),
            pessoa.getNome(),
            pessoa.getMae(),
            pessoa.getPai(),
            pessoa.getSexo(),
            pessoa.getDataNascimento(),                        
            (endereco != null) ? endereco.getTipoLogradouro() : null,
            (endereco != null) ? endereco.getLogradouro() : null,
            (endereco != null) ? endereco.getNumero() : null,
            (endereco != null) ? endereco.getBairro() : null,
            (endereco != null && endereco.getCidade() != null) ? endereco.getCidade().getNome() : null,
            "Servidor_Temporário",
            servidorTemporario.getDataAdmissao(),
            servidorTemporario.getDataDemissao()
        );
    }
}
