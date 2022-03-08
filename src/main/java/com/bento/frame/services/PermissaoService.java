package com.bento.frame.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.bento.frame.dtos.PermissaoDto;
import com.bento.frame.models.Permissao;
import com.bento.frame.repositories.PermissaoRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PermissaoService {
	
	private final PermissaoRepository permissaoRepository;
	
	public PermissaoService (PermissaoRepository permissaoRepository) {
		this.permissaoRepository = permissaoRepository;
	}
	
	public PermissaoDto adicionarPermissao(PermissaoDto dto) {
		Permissao permissao = new Permissao();
		try {
			BeanUtils.copyProperties(dto, permissao);
			permissaoRepository.save(permissao);
			BeanUtils.copyProperties(permissao, dto);
		} catch (Exception e) {
			log.error("error: {}", e.getMessage());
		}
		return dto;
	}
	
	public List<PermissaoDto> listarPermissoes() {
		List<Permissao> permissoes = permissaoRepository.findAll();
		List<PermissaoDto> dtos = new ArrayList<>();
		
		for (Permissao permissao : permissoes) {
			PermissaoDto dto = new PermissaoDto();
			BeanUtils.copyProperties(permissao, dto);
			dtos.add(dto);
		}
		return dtos;
	}
	
	public PermissaoDto atualizarPermissao(PermissaoDto dto) {
		Optional<Permissao> permissao = permissaoRepository.findById(dto.getId());		
		
		if (permissao.isPresent()) {
			try {			
				BeanUtils.copyProperties(dto, permissao);
			} catch (Exception e) {
				log.error("error: {}", e.getMessage());
			}
			permissaoRepository.save(permissao.get());
		}
		return dto;
	}
	
	public boolean deletarPermissao(Long id) {
		boolean resultado = false;
		
		try {
			permissaoRepository.deleteById(id);
			resultado = true;
		} catch (Exception e) {
			log.error("error: {}", e.getMessage());
		}
		return resultado;
	}
	
}
