package com.rossatti.spring_pjc_2025.lotacao.services;

import com.rossatti.spring_pjc_2025.lotacao.dtos.request.LotacaoRequest;
import com.rossatti.spring_pjc_2025.lotacao.dtos.response.LotacaoResponse;
import com.rossatti.spring_pjc_2025.lotacao.exceptions.LotacaoNotFoundException;
import com.rossatti.spring_pjc_2025.lotacao.mappers.LotacaoMapper;
import com.rossatti.spring_pjc_2025.lotacao.models.Lotacao;
import com.rossatti.spring_pjc_2025.lotacao.repositories.LotacaoRepository;
import com.rossatti.spring_pjc_2025.pessoa.exceptions.PessoaNotFoundException;
import com.rossatti.spring_pjc_2025.pessoa.models.Pessoa;
import com.rossatti.spring_pjc_2025.pessoa.repositories.PessoaRepository;
import com.rossatti.spring_pjc_2025.unidade.exceptions.UnidadeNotFoundException;
import com.rossatti.spring_pjc_2025.unidade.models.Unidade;
import com.rossatti.spring_pjc_2025.unidade.repositories.UnidadeRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LotacaoServiceImpl implements LotacaoService {

    private final LotacaoRepository lotacaoRepository;
    private final PessoaRepository pessoaRepository;
    private final UnidadeRepository unidadeRepository;
    private final LotacaoMapper lotacaoMapper;

    public LotacaoServiceImpl(
        LotacaoRepository lotacaoRepository, 
        PessoaRepository pessoaRepository, 
        UnidadeRepository unidadeRepository,
        LotacaoMapper lotacaoMapper
        ) {
        this.lotacaoRepository = lotacaoRepository;
        this.pessoaRepository = pessoaRepository;
        this.unidadeRepository = unidadeRepository;
        this.lotacaoMapper = lotacaoMapper;
    }

    @Override
    @Transactional
    public LotacaoResponse criarLotacao(LotacaoRequest request) {
        Pessoa pessoa = pessoaRepository.findById(request.getPessoaId())
                .orElseThrow(() -> new PessoaNotFoundException("Pessoa não encontrada com ID: " + request.getPessoaId()));

        Unidade unidade = unidadeRepository.findById(request.getUnidadeId())
                .orElseThrow(() -> new PessoaNotFoundException("Unidade não encontrada com ID: " + request.getUnidadeId()));

        Lotacao lotacao = Lotacao.builder()
                .pessoa(pessoa)
                .unidade(unidade)
                .dataLotacao(request.getDataLotacao())
                .dataRemocao(request.getDataRemocao())
                .portaria(request.getPortaria())
                .build();

        lotacao = lotacaoRepository.save(lotacao);
        
        return lotacaoMapper.toResponse(lotacao);
    }

    @Override
    @Transactional
    public LotacaoResponse atualizarLotacao(Long id, LotacaoRequest request) {
        Lotacao lotacao = lotacaoRepository.findById(id)
                .orElseThrow(() -> new UnidadeNotFoundException("Lotação não encontrada com ID: " + id));

        Pessoa pessoa = pessoaRepository.findById(request.getPessoaId())
                .orElseThrow(() -> new UnidadeNotFoundException("Pessoa não encontrada com ID: " + request.getPessoaId()));

        Unidade unidade = unidadeRepository.findById(request.getUnidadeId())
                .orElseThrow(() -> new UnidadeNotFoundException("Unidade não encontrada com ID: " + request.getUnidadeId()));

        lotacao.setPessoa(pessoa);
        lotacao.setUnidade(unidade);
        lotacao.setDataLotacao(request.getDataLotacao());
        lotacao.setDataRemocao(request.getDataRemocao());
        lotacao.setPortaria(request.getPortaria());

        lotacao = lotacaoRepository.save(lotacao);

        return lotacaoMapper.toResponse(lotacao);

    }

    @Override
    @Transactional(readOnly = true)
    public LotacaoResponse buscarPorId(Long id) {
        Lotacao lotacao = lotacaoRepository.findById(id)
                .orElseThrow(() -> new LotacaoNotFoundException("Lotação não encontrada com ID: " + id));

        return lotacaoMapper.toResponse(lotacao)        ;        
    }

    @Override
    @Transactional(readOnly = true)
    public List<LotacaoResponse> listarTodas() {
        return lotacaoRepository.findAll()
                .stream()
//                .map(this::converterParaResponse)
                .map(lotacaoMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deletarLotacao(Long id) {
        if (!lotacaoRepository.existsById(id)) {
            throw new LotacaoNotFoundException("Lotação não encontrada com ID: " + id);
        }
        lotacaoRepository.deleteById(id);
    }
/*
    private LotacaoResponse converterParaResponse(Lotacao lotacao) {
        return LotacaoResponse.builder()
                .id(lotacao.getId())
                .pessoaId(lotacao.getPessoa().getId())
                .pessoaNome(lotacao.getPessoa().getNome())
                .unidadeId(lotacao.getUnidade().getId())
                .unidadeNome(lotacao.getUnidade().getNome())
                .dataLotacao(lotacao.getDataLotacao())
                .dataRemocao(lotacao.getDataRemocao())
                .portaria(lotacao.getPortaria())
                .build();
    }
*/                
}
