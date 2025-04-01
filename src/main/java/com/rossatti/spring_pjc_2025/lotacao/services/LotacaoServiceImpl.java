package com.rossatti.spring_pjc_2025.lotacao.services;

import com.rossatti.spring_pjc_2025.endereco.entities.Endereco;
import com.rossatti.spring_pjc_2025.lotacao.dtos.request.LotacaoRequest;
import com.rossatti.spring_pjc_2025.lotacao.dtos.response.LotacaoResponse;
import com.rossatti.spring_pjc_2025.lotacao.entities.Lotacao;
import com.rossatti.spring_pjc_2025.lotacao.exceptions.LotacaoNotFoundException;
import com.rossatti.spring_pjc_2025.lotacao.mappers.LotacaoMapper;
import com.rossatti.spring_pjc_2025.lotacao.repositories.LotacaoRepository;
import com.rossatti.spring_pjc_2025.pessoa.entities.Pessoa;
import com.rossatti.spring_pjc_2025.pessoa.exceptions.PessoaNotFoundException;
import com.rossatti.spring_pjc_2025.pessoa.repositories.PessoaRepository;
import com.rossatti.spring_pjc_2025.pessoa_servidor_efetivo.dtos.response.EnderecoFuncionalResponseDTO;
import com.rossatti.spring_pjc_2025.unidade.entities.Unidade;
import com.rossatti.spring_pjc_2025.unidade.entities.UnidadeEndereco;
import com.rossatti.spring_pjc_2025.unidade.exceptions.UnidadeNotFoundException;
import com.rossatti.spring_pjc_2025.unidade.repositories.UnidadeRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public LotacaoResponse create(LotacaoRequest request) {
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
    public LotacaoResponse update(Long id, LotacaoRequest request) {
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
    public LotacaoResponse findById(Long id) {
        if (!lotacaoRepository.existsById(id)) {
                return new LotacaoResponse();                
        }
        return lotacaoRepository.findById(id)
            .map(lotacaoMapper::toResponse)
            .orElseThrow(LotacaoNotFoundException::new);
    }

    @Override
//    @Transactional(readOnly = true)
    public Page<LotacaoResponse> findAll(Pageable pageable) {
            return lotacaoRepository.findAll(pageable)
                    .map(lotacaoMapper::toResponse);
        // return lotacaoRepository.findByNomeContaining(nome, pageable)
        //        .map(lotacaoMapper::toResponse) ;
    }

    public Page<EnderecoFuncionalResponseDTO> buscarEnderecoFuncionalPorNome(String nome, Pageable pageable) {
        Page<Lotacao> lotacaoPage = lotacaoRepository.findByPessoaNomeContaining(nome, pageable);

        return lotacaoPage.map(lotacao -> {
                Unidade unidade = lotacao.getUnidade();
                UnidadeEndereco unidadeEndereco = unidade.getUnidadeEndereco(); // Buscar via tabela intermediária
                Endereco endereco = unidadeEndereco != null ? unidadeEndereco.getEndereco() : null;

                return new EnderecoFuncionalResponseDTO(
                lotacao.getPessoa().getNome(),
                unidade.getNome(),
                endereco != null ? endereco.getLogradouro() : "Não informado",
                endereco != null ? endereco.getNumero() : 0,
                endereco != null ? endereco.getBairro() : "Não informado",
                endereco != null ? endereco.getCidade().getNome() : "Não informado",
                endereco != null ? endereco.getCidade().getUf() : "Não informado"
                );
        });
    }

/*
    public Page<EnderecoFuncionalResponseDTO> buscarEnderecoFuncionalPorNome(String nome, Pageable pageable) {
        Page<Lotacao> lotacaoPage = lotacaoRepository.findByPessoaNomeContaining(nome, pageable);

        return lotacaoPage.map(lotacao -> new EnderecoFuncionalResponseDTO(
                lotacao.getPessoa().getNome()
                ,lotacao.getUnidade().getNome()
                ,lotacao.getUnidade().getEndereco().getLogradouro()
                ,lotacao.getUnidade().getEndereco().getNumero()
                ,lotacao.getUnidade().getEndereco().getBairro()
                ,lotacao.getUnidade().getEndereco().getCidade().getNome()
                ,lotacao.getUnidade().getEndereco().getCidade().getUf()
                )
        );
    }
*/        

}    