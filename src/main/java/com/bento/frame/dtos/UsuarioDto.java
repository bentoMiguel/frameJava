package com.bento.frame.dtos;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class UsuarioDto {
	
	private long id;
	@NotBlank
	private String login;
	@NotBlank
	private String password;

}
