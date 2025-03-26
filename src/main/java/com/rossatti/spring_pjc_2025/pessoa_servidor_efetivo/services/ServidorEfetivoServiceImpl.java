package com.rossatti.spring_pjc_2025.pessoa_servidor_efetivo.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import static org.springframework.http.HttpStatus.*;

import com.rossatti.spring_pjc_2025.lotacao.models.Lotacao;
import com.rossatti.spring_pjc_2025.lotacao.repositories.LotacaoRepository;
import com.rossatti.spring_pjc_2025.pessoa.models.Pessoa;
import com.rossatti.spring_pjc_2025.pessoa.repositories.PessoaRepository;
import com.rossatti.spring_pjc_2025.pessoa_foto.exceptions.PessoaFotoNotFoundException;
import com.rossatti.spring_pjc_2025.pessoa_servidor_efetivo.dtos.request.ServidorEfetivoRequest;
import com.rossatti.spring_pjc_2025.pessoa_servidor_efetivo.entity.ServidorEfetivo;
import com.rossatti.spring_pjc_2025.pessoa_servidor_efetivo.repositories.ServidorEfetivoRepository;
import com.rossatti.spring_pjc_2025.pessoa_servidor_temporario.dtos.request.ServidorTemporarioRequest;
import com.rossatti.spring_pjc_2025.pessoa_servidor_temporario.models.ServidorTemporario;
import com.rossatti.spring_pjc_2025.unidade.models.Unidade;
import com.rossatti.spring_pjc_2025.unidade.repositories.UnidadeRepository;

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
        LotacaoRepository lotacaoRepository) {
            this.servidorEfetivoRepository = servidorEfetivoRepository;
            this.pessoaRepository = pessoaRepository;
            this.unidadeRepository = unidadeRepository;
            this.lotacaoRepository = lotacaoRepository;    
        }        

    @Override
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

        // 🔹 Criar e salvar Servidor Temporário
        ServidorEfetivo servidorTemporario = new ServidorEfetivo();
        servidorTemporario.setPessoa(pessoaOpt.get());
        servidorTemporario.setId(dto.getPessoaId());        
//        servidorTemporario.setDataAdmissao(dto.getDataAdmissao());
        servidorEfetivoRepository.save(servidorTemporario);

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
