package com.bento.frame.dtos;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class PermissaoDto {
	
	private long id;
	@NotBlank
	private String nome;
	@NotBlank
	private boolean criar;
	@NotBlank
	private boolean ler;
	@NotBlank
	private boolean atualizar;
	@NotBlank
	private boolean deletar;
	
}
