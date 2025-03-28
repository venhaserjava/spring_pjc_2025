package com.rossatti.spring_pjc_2025.pessoa_servidor_temporario.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import static org.springframework.http.HttpStatus.*;

import com.rossatti.spring_pjc_2025.lotacao.entities.Lotacao;
import com.rossatti.spring_pjc_2025.lotacao.repositories.LotacaoRepository;
import com.rossatti.spring_pjc_2025.pessoa.entities.Pessoa;
import com.rossatti.spring_pjc_2025.pessoa.repositories.PessoaRepository;
import com.rossatti.spring_pjc_2025.pessoa_servidor_temporario.dtos.request.ServidorTemporarioRequest;
import com.rossatti.spring_pjc_2025.pessoa_servidor_temporario.models.ServidorTemporario;
import com.rossatti.spring_pjc_2025.pessoa_servidor_temporario.repositories.ServidorTemporarioRepository;
import com.rossatti.spring_pjc_2025.unidade.entities.Unidade;
import com.rossatti.spring_pjc_2025.unidade.repositories.UnidadeRepository;

@Service
public class ServidorTemporarioServiceImpl implements ServidorTemporarioService {

    private final ServidorTemporarioRepository servidorTemporarioRepository;
    private final PessoaRepository pessoaRepository;
    private final UnidadeRepository unidadeRepository;
    private final LotacaoRepository lotacaoRepository;

    public ServidorTemporarioServiceImpl(
            ServidorTemporarioRepository servidorTemporarioRepository,
            PessoaRepository pessoaRepository,
            UnidadeRepository unidadeRepository,
            LotacaoRepository lotacaoRepository) {
        this.servidorTemporarioRepository = servidorTemporarioRepository;
        this.pessoaRepository = pessoaRepository;
        this.unidadeRepository = unidadeRepository;
        this.lotacaoRepository = lotacaoRepository;
    }

    @Transactional
    public void cadastrarServidorTemporario(ServidorTemporarioRequest dto) {

        List<String> erros = new ArrayList<>();

        // 🔹 Validação da Pessoa
        Optional<Pessoa> pessoaOpt = pessoaRepository.findById(dto.getPessoaId());
        if (pessoaOpt.isEmpty()) {
            erros.add("Pessoa não encontrada.");
        }

        // 🔹 Validação da Data de Admissão
        LocalDate dataNascimento = pessoaOpt.map(Pessoa::getDataNascimento).orElse(null);
        if (dataNascimento != null && dataNascimento.plusYears(18).isAfter(dto.getDataAdmissao())) {
            erros.add("A Data de admissão deve ser posterior ao 18º aniversário da pessoa.");
        }

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
        if (dataLotacao.isBefore(dto.getDataAdmissao())) {
            erros.add("Data de lotação deve ser maior ou igual à data de admissão.");
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

        // 🔹 Criar e salvar Servidor Temporário
        ServidorTemporario servidorTemporario = new ServidorTemporario();
        servidorTemporario.setPessoa(pessoaOpt.get());
        servidorTemporario.setPessoaId(dto.getPessoaId());        
        servidorTemporario.setDataAdmissao(dto.getDataAdmissao());
        servidorTemporarioRepository.save(servidorTemporario);

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
}
