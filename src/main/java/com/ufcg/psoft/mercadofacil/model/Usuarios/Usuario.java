package com.ufcg.psoft.mercadofacil.model.Usuarios;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;

import org.springframework.data.annotation.Id;

@Entity
public class Usuario {

	@GeneratedValue
	@Id
	private Long id;
	private Long cpf;
	private String nome;
	private Perfil perfil;
	private BigDecimal desconto;

	public Usuario() {

	}

	public Usuario(String nome, Perfil perfil, Long cpf) {
		this.nome = nome;
		this.perfil = perfil;
		this.desconto = this.setDesconto();
	}

	public BigDecimal setDesconto() {
		if (this.perfil.equals("NORMAL")) {
			return new BigDecimal(0);
		} else if(this.perfil.equals("ESPECIAL")) {
			return new BigDecimal(0.1);
		} else if(this.perfil.equals("PREMIUM") ) {
			return new BigDecimal(0.1);
		}
		return null;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public Long getCPF() {
		return this.cpf;
	}

	
	public Perfil getPerfil() {
		return this.perfil;
	}

	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
	}

	public BigDecimal getDesconto() {
		return this.desconto;
	}
	
	public void setDesconto(BigDecimal desconto) {
		this.desconto = desconto;
	}
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
