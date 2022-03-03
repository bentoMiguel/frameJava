package com.bento.frame.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.bento.frame.dtos.UsuarioDto;
import com.bento.frame.services.UsuarioService;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {
	
	private final UsuarioService usuarioService;
	
	public UsuarioController (UsuarioService usuarioService) {
		this.usuarioService = usuarioService;
	}
	
	@GetMapping("/usuarios")
	public List<UsuarioDto> getUsuarios() {
		return usuarioService.getUsuarios();
	}

	@GetMapping("/id/{id}")
	public UsuarioDto getUsuario(@PathVariable(value = "id") long id) {
		return usuarioService.getUsuario(id);
	}
	
	@GetMapping("/login/{login}")
	public UsuarioDto getUsuarios(@PathVariable(value = "login") String login) {
		return usuarioService.getUsuario(login);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public UsuarioDto adicionarUsuario(@RequestBody UsuarioDto dto) {
		return usuarioService.adicionarUsuario(dto);
	}
	
	@PutMapping()
	public UsuarioDto updateUsuario(//@PathVariable(value = "id") long id,
			@Valid @RequestBody UsuarioDto dto) {
		return usuarioService.updateUser(dto);
	}
	
	
}
