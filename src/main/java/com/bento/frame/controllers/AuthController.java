package com.bento.frame.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.bento.frame.dtos.LoginResultDto;
import com.bento.frame.dtos.UsuarioDto;
import com.bento.frame.services.JwtHelper;
import com.bento.frame.services.UsuarioService;

@RestController
public class AuthController {
	
	private final JwtHelper jwtHelper;
	private final PasswordEncoder passwordEncoder;
	private final UsuarioService usuarioService;
	
	public AuthController(JwtHelper jwtHelper, PasswordEncoder passwordEncoder, 
			UsuarioService usuarioService) {
		this.jwtHelper = jwtHelper;
		this.passwordEncoder = passwordEncoder;
		this.usuarioService = usuarioService;
	}
	
	@PostMapping(path = "login", consumes = { MediaType.APPLICATION_FORM_URLENCODED_VALUE })
	public LoginResultDto login(
			@RequestParam String login,
			@RequestParam String password) {
		
		UserDetails userDetails;
		try {
			UsuarioDto dto = usuarioService.getUsuario(login);
			if (dto.getId() == 0) {
				throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found");
			}
			userDetails = usuarioService.getUserDetail(dto);
		} catch (UsernameNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found");
		}
		
		if (passwordEncoder.matches(password, userDetails.getPassword())) {
			Map<String, String> claims = new HashMap<>();
			claims.put("username", login);
			claims.put("userId", String.valueOf(1));
			
			String jwt = jwtHelper.createJwtForClaims(login, claims);
			return new LoginResultDto(jwt);
		}
		
		throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not authenticated");
	}

}
