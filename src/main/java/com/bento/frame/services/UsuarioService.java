package com.bento.frame.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.bento.frame.dtos.UsuarioDto;
import com.bento.frame.models.Usuario;
import com.bento.frame.repositories.UsuarioRepository;
import com.bento.frame.utils.Crypt;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UsuarioService {
	
	private final UsuarioRepository usuarioRepository;
	private final PasswordEncoder passwordEncoder;
	
	public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
		this.usuarioRepository = usuarioRepository;
		this.passwordEncoder = passwordEncoder;
	}
	
	public List<UsuarioDto> getUsuarios() {
		ArrayList<UsuarioDto> dtos = new ArrayList<>();
		List<Usuario> usuarios = usuarioRepository.findAll();
		
		for (Usuario usuario : usuarios) {
			UsuarioDto dto = new UsuarioDto();
			BeanUtils.copyProperties(usuario, dto);
			dtos.add(dto);
		}
		return dtos;
	}
	
	public UsuarioDto getUsuario(String login) {
		Usuario usuario = usuarioRepository.findByLogin(login);
		if (usuario.getPsw() != null) {
			try {
				usuario.setPassword(Crypt.decrypt(usuario.getPsw()));
			} catch (Exception e) {
				log.error("error: {}", e.getMessage());
			}
		}
		UsuarioDto dto = new UsuarioDto();
		BeanUtils.copyProperties(usuario, dto);
		return dto;
	}
	
	public UsuarioDto getUsuario(Long id) {
		Optional<Usuario> usuario = usuarioRepository.findById(id);
		if (usuario.isPresent() && usuario.get().getPsw() != null) {
			try {
				usuario.get().setPassword(Crypt.decrypt(usuario.get().getPsw()));
			} catch (Exception e) {
				log.error("error: {}", e.getMessage());
			}
		}
		UsuarioDto dto = new UsuarioDto();
		BeanUtils.copyProperties(usuario.get(), dto);
		return dto;
	}
	
    public UsuarioDto adicionarUsuario(UsuarioDto dto) {
    	Usuario usuario = new Usuario();
    	BeanUtils.copyProperties(dto, usuario);
    	if (dto.getPassword() != null) {
    		try {
    			usuario.setPsw(Crypt.encrypt(dto.getPassword()));
    		} catch (Exception e) {
    			log.error("error: {}", e.getMessage());
    		}
    	}
    	usuario = usuarioRepository.save(usuario);
    	BeanUtils.copyProperties(usuario, dto);
		return dto;
	}
    
    public UsuarioDto updateUser(UsuarioDto dto) {
    	Optional<Usuario> user = usuarioRepository.findById(dto.getId());
    	if (user.isPresent()) {
    		BeanUtils.copyProperties(dto, user.get());
    		if (dto.getPassword() != null) {
    			try {
					user.get().setPsw(Crypt.encrypt(dto.getPassword()));
				} catch (Exception e) {
					log.error("error: {}", e.getMessage());
				}
    		}
    		usuarioRepository.save(user.get());
    	}
    	return dto;
    } 
    
    public UserDetails getUserDetail(UsuarioDto dto) {
    	UserDetails user1 = User
				.withUsername(dto.getLogin())
				.authorities(new ArrayList<GrantedAuthority>())
				.passwordEncoder(passwordEncoder::encode)
				.password(dto.getPassword())
				.build();
		
    	return user1;
    }

}
