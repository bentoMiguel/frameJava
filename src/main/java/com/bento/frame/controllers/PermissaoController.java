package com.bento.frame.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.bento.frame.dtos.PermissaoDto;
import com.bento.frame.services.PermissaoService;

@RestController
@RequestMapping("/permissao")
public class PermissaoController {
	
	private final PermissaoService permissaoService;
	
	public PermissaoController(PermissaoService permissaoService) {
		this.permissaoService = permissaoService;
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public PermissaoDto adicionarPermissao(@RequestBody PermissaoDto permissao) {
		return permissaoService.adicionarPermissao(permissao);
	}
	
	@GetMapping
	public List<PermissaoDto> listarPermissoes() {
		return permissaoService.listarPermissoes();
	}
	
	@PutMapping
	@ResponseStatus(HttpStatus.ACCEPTED)
	public PermissaoDto atualizarPermissao(@RequestBody PermissaoDto permissao) {
		return permissaoService.atualizarPermissao(permissao);
	}
	
	@DeleteMapping("/{id}")
	public void deletarPermissao(@PathVariable(value = "id") Long id) {
		permissaoService.deletarPermissao(id);
	}
	

}
