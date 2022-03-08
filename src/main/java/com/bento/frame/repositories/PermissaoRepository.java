package com.bento.frame.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bento.frame.models.Permissao;

public interface PermissaoRepository extends JpaRepository<Permissao, Long> {

}
