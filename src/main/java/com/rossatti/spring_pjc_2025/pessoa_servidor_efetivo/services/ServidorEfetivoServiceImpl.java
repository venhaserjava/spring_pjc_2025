package com.rossatti.spring_pjc_2025.pessoa_servidor_efetivo.services;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;




import org.springframework.web.server.ResponseStatusException;
import static org.springframework.http.HttpStatus.*;

import com.rossatti.spring_pjc_2025.lotacao.entities.Lotacao;
import com.rossatti.spring_pjc_2025.lotacao.repositories.LotacaoRepository;
import com.rossatti.spring_pjc_2025.pessoa.entities.Pessoa;
import com.rossatti.spring_pjc_2025.pessoa.repositories.PessoaRepository;
import com.rossatti.spring_pjc_2025.pessoa_servidor_efetivo.dtos.request.ServidorEfetivoRequest;
import com.rossatti.spring_pjc_2025.pessoa_servidor_efetivo.dtos.request.ServidorEfetivoRequestDTO;
import com.rossatti.spring_pjc_2025.pessoa_servidor_efetivo.dtos.response.ServidorEfetivoResponseDTO;
import com.rossatti.spring_pjc_2025.pessoa_servidor_efetivo.entities.ServidorEfetivo;
//import com.rossatti.spring_pjc_2025.pessoa_servidor_efetivo.entities.ServidorEfetivoId;
import com.rossatti.spring_pjc_2025.pessoa_servidor_efetivo.exceptions.ServidorEfetivoNotFoundException;
import com.rossatti.spring_pjc_2025.pessoa_servidor_efetivo.repositories.ServidorEfetivoRepository;
import com.rossatti.spring_pjc_2025.unidade.entities.Unidade;
import com.rossatti.spring_pjc_2025.unidade.repositories.UnidadeRepository;

import jakarta.transaction.Transactional;

@Service
public class ServidorEfetivoServiceImpl implements ServidorEfetivoService {

    private final ServidorEfetivoRepository servidorEfetivoRepository;
    private final PessoaRepository pessoaRepository;
    private final UnidadeRepository unidadeRepository;
    private final LotacaoRepository lotacaoRepository;
    
    public ServidorEfetivoServiceImpl(    
        ServidorEfetivoRepository servidorEfetivoRepository,
        PessoaRepository pessoaRepository,
        UnidadeRepository unidadeRepository,
        LotacaoRepository lotacaoRepository
    ) {
            this.servidorEfetivoRepository = servidorEfetivoRepository;
            this.pessoaRepository = pessoaRepository;
            this.unidadeRepository = unidadeRepository;
            this.lotacaoRepository = lotacaoRepository;    
    }        

    @Override
    @Transactional
    public ServidorEfetivoResponseDTO criarServidorEfetivo(ServidorEfetivoRequestDTO request) {

        if (servidorEfetivoRepository.existsByPessoaId(request.getPessoaId())) {
            throw new IllegalArgumentException("Servidor efetivo já cadastrado!");
        }
    
        Pessoa pessoa = pessoaRepository.findById(request.getPessoaId())
            .orElseThrow(() -> new RuntimeException("Pessoa não encontrada para o ID: " + request.getPessoaId()));        
    
        // Criando o ServidorEfetivo corretamente sem chave composta
        ServidorEfetivo servidorEfetivo = ServidorEfetivo.builder()
//                .id(pessoa.getId()) // Agora id é apenas Long
                .matricula(request.getMatricula()) // Matricula é um campo separado
                .pessoa(pessoa)
                .build();
        
        servidorEfetivo = servidorEfetivoRepository.save(servidorEfetivo);
    
        Unidade unidade = unidadeRepository.findById(request.getUnidadeId())
            .orElseThrow(() -> new RuntimeException("Unidade não encontrada para o ID: " + request.getUnidadeId()));
    
        Lotacao lotacao = Lotacao.builder()
                .pessoa(servidorEfetivo.getPessoa())
                .unidade(unidade)
                .dataLotacao(LocalDate.now())
                .portaria(request.getPortaria())
                .build();
        
        lotacao = lotacaoRepository.save(lotacao);
        
        // Retornar DTO de resposta corrigido
        return new ServidorEfetivoResponseDTO(
            servidorEfetivo.getId(), // Agora o ID é apenas Long
            pessoa.getNome(),
            calcularIdade(pessoa.getDataNascimento()),            
            servidorEfetivo.getMatricula(), // Matricula agora é um campo normal
            unidade.getNome(),
            lotacao.getDataLotacao(),
            lotacao.getPortaria()
        );
    }
    

        @Override
        public Page<ServidorEfetivoResponseDTO> listarServidoresPorUnidade(Long unidadeId, Pageable pageable) {

            Pageable pageableComOrdenacao = PageRequest.of(
                pageable.getPageNumber(),
                pageable.getPageSize(),
                Sort.by("unidade.nome") 
            );            


            Page<Lotacao> lotacaoPage = lotacaoRepository.findByUnidadeId(unidadeId, pageableComOrdenacao);
            List<Lotacao> lotacoes = lotacaoPage.getContent();
        
            // Coletar os IDs das pessoas
            List<Long> pessoaIds = lotacoes.stream()
                    .map(lotacao -> lotacao.getPessoa().getId())
                    .collect(Collectors.toList());
        
            // Buscar todos os servidores efetivos de uma vez e armazenar em um Map
            Map<Long, ServidorEfetivo> servidoresMap = servidorEfetivoRepository.findByPessoaIdIn(pessoaIds)
                    .stream()
                    .collect(Collectors.toMap(se -> se.getPessoa().getId(), se -> se));
        
            // Mapear os resultados para DTO
            List<ServidorEfetivoResponseDTO> dtos = lotacoes.stream()
                    .map(lotacao -> {
                        ServidorEfetivo servidorEfetivo = servidoresMap.get(lotacao.getPessoa().getId());
        
                        if (servidorEfetivo == null) {
                            throw new ServidorEfetivoNotFoundException("Servidor Efetivo não encontrado para Pessoa ID: " + lotacao.getPessoa().getId());
                        }
        
                        return new ServidorEfetivoResponseDTO(
                                servidorEfetivo.getId(), // Agora `se_id`
                                lotacao.getPessoa().getNome(),
                                calcularIdade(lotacao.getPessoa().getDataNascimento()),
                                servidorEfetivo.getMatricula(),
                                lotacao.getUnidade().getNome(),
                                lotacao.getDataLotacao(),
                                lotacao.getPortaria()
                        );
                    })
                    .collect(Collectors.toList());
        
            return new PageImpl<>(dtos, pageable, lotacaoPage.getTotalElements());
        }      

        @Override
        @Transactional
    public void cadastrarServidorEfetivo(ServidorEfetivoRequest dto) {

        List<String> erros = new ArrayList<>();

        // 🔹 Validação da Pessoa
        Optional<Pessoa> pessoaOpt = pessoaRepository.findById(dto.getPessoaId());
        if (pessoaOpt.isEmpty()) {
            erros.add("Pessoa não encontrada.");
        }
        // // 🔹 Validação da Data de Admissão
         LocalDate dataNascimento = pessoaOpt.map(Pessoa::getDataNascimento).orElse(null);
        // if (dataNascimento != null && dataNascimento.plusYears(18).isAfter(dto.getDataAdmissao())) {
        //     erros.add("A Data de admissão deve ser posterior ao 18º aniversário da pessoa.");
        // }
        // 🔹 Validação da Unidade
        Optional<Unidade> unidadeOpt = unidadeRepository.findById(dto.getLotacao().getUnidadeId());
        if (unidadeOpt.isEmpty()) {
            erros.add("Unidade não encontrada.");
        }
        // 🔹 Validação da Data de Lotação
        LocalDate dataLotacao = dto.getLotacao().getDataLotacao();
        if (dataNascimento != null && dataNascimento.plusYears(18).isAfter(dataLotacao)) {
            erros.add("A Data de lotação deve ser posterior ao 18º aniversário da pessoa.");
        }
        // 🔹 Validação da Portaria
        String portaria = dto.getLotacao().getPortaria();
        if (portaria == null || portaria.trim().isEmpty()) {
            erros.add("Portaria é obrigatória e não pode estar vazia ou conter apenas espaços.");
        }

        // 🔹 Se houver erros, lançar exceção com JSON no formato esperado
        if (!erros.isEmpty()) {
            throw new ResponseStatusException(BAD_REQUEST, criarMensagemErro(erros));
        }

        // 🔹 Criar e salvar Servidor Efetivo
        ServidorEfetivo servidorEfetivo = new ServidorEfetivo();
        servidorEfetivo.setPessoa(pessoaOpt.get());
//        servidorEfetivo.setId(new ServidorEfetivoId(dto.getPessoaId(),dto.getMatricula()) );        
        servidorEfetivo.setId(dto.getPessoaId() );        
        servidorEfetivoRepository.save(servidorEfetivo);

        // 🔹 Criar e salvar Lotação
        Lotacao lotacao = new Lotacao();
        lotacao.setPessoa(pessoaOpt.get());
        lotacao.setUnidade(unidadeOpt.get());
        lotacao.setDataLotacao(dto.getLotacao().getDataLotacao());
        lotacao.setPortaria(dto.getLotacao().getPortaria());
        lotacaoRepository.save(lotacao);
    }

    private String criarMensagemErro(List<String> erros) {

        if (erros.size() == 1) {
            return "{\"erro\": \"" + erros.get(0) + "\"}";
        } else {
            return "{\"erros\": " + erros.toString() + "}";
        }
    }

    // Método para calcular a idade
    private int calcularIdade(LocalDate dataNascimento) {
        return Period.between(dataNascimento, LocalDate.now()).getYears();
    }



}
