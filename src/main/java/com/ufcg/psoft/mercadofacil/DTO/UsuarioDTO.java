package com.ufcg.psoft.mercadofacil.DTO;

import java.math.BigDecimal;

import com.ufcg.psoft.mercadofacil.model.Usuarios.Perfil;
import com.ufcg.psoft.mercadofacil.model.Usuarios.Usuario;

public class UsuarioDTO {

	Usuario usuario;
	
	public UsuarioDTO(Usuario usuario) {
		this.usuario = usuario;
	}


	public static UsuarioDTO objToDTO(Usuario usuario) {
		return new UsuarioDTO(usuario);
	}
	
	public Usuario getUsuario() {
		return this.usuario;
	}
	
	public String getNome() {
		return this.usuario.getNome();
	}
	
	public BigDecimal getDesconto() {
		return this.usuario.getDesconto();
	}
	
	public Perfil getPerfil() {
		return this.usuario.getPerfil();
	}
}
