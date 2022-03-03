package com.bento.frame.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bento.frame.models.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
	
	Usuario findByLogin(String login);

}
