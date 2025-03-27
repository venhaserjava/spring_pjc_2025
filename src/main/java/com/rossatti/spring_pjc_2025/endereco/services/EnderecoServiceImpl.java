package com.rossatti.spring_pjc_2025.endereco.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.rossatti.spring_pjc_2025.cidade.entitys.Cidade;
import com.rossatti.spring_pjc_2025.cidade.exceptions.CidadeNotFoundException;
import com.rossatti.spring_pjc_2025.cidade.repositories.CidadeRepository;
import com.rossatti.spring_pjc_2025.endereco.dtos.request.EnderecoRequest;
import com.rossatti.spring_pjc_2025.endereco.dtos.response.EnderecoResponse;
import com.rossatti.spring_pjc_2025.endereco.entity.Endereco;
import com.rossatti.spring_pjc_2025.endereco.exceptions.EnderecoNotFoundException;
import com.rossatti.spring_pjc_2025.endereco.mappers.EnderecoMapper;
import com.rossatti.spring_pjc_2025.endereco.repositories.EnderecoRepository;
import jakarta.transaction.Transactional;

@Service
public class EnderecoServiceImpl implements EnderecoService {
    private final EnderecoRepository enderecoRepository;
    private final CidadeRepository cidadeRepository;
    private final EnderecoMapper enderecoMapper;

    public EnderecoServiceImpl(EnderecoRepository enderecoRepository, CidadeRepository cidadeRepository, EnderecoMapper enderecoMapper) {
        this.enderecoRepository = enderecoRepository;
        this.cidadeRepository = cidadeRepository;
        this.enderecoMapper = enderecoMapper;
    }

    @Override
    @Transactional
    public EnderecoResponse create(EnderecoRequest dto) {
        Cidade cidade = cidadeRepository.findById(dto.getCidadeId())
                .orElseThrow(() -> new CidadeNotFoundException("Cidade não encontrada"));
        Endereco endereco = enderecoMapper.toModel(dto, cidade);
        endereco = enderecoRepository.save(endereco);
        return enderecoMapper.toResponse(endereco);
    }

    @Override
    public EnderecoResponse findById(Long id) {
        Endereco endereco = enderecoRepository.findById(id)
                .orElseThrow(() -> new EnderecoNotFoundException("Endereço não encontrado"));
        return enderecoMapper.toResponse(endereco);
    }

    @Override
    public Page<EnderecoResponse> findAll(Pageable pageable) {
        return enderecoRepository.findAll(pageable)
            .map(enderecoMapper::toResponse);
    }

    @Override
    @Transactional
    public EnderecoResponse update(Long id, EnderecoRequest dto) {
        Endereco endereco = enderecoRepository.findById(id)
                .orElseThrow(() -> new EnderecoNotFoundException("Endereço não encontrado"));
        Cidade cidade = cidadeRepository.findById(dto.getCidadeId())
                .orElseThrow(() -> new CidadeNotFoundException("Cidade não encontrada"));
        endereco = enderecoMapper.toModel(dto, cidade);
        endereco.setId(id);
        endereco = enderecoRepository.save(endereco);
        return enderecoMapper.toResponse(endereco);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!enderecoRepository.existsById(id)) {
            throw new EnderecoNotFoundException("Endereço não encontrado");
        }
        enderecoRepository.deleteById(id);
    }
}
