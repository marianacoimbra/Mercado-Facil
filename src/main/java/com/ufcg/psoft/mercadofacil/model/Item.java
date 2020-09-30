package com.ufcg.psoft.mercadofacil.model;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;


@Entity
public class Item {
	@Id
	@GeneratedValue
	private Long id;
	private BigDecimal preco;
	@OneToOne
	private Produto produto;
	private Integer quantidade;
	
	
	
	
	public Item(Produto produto, int quantidade) {
	 this.produto = produto;
	 this.quantidade = quantidade;
	 
	 preco = produto.getPreco().multiply(BigDecimal.valueOf(quantidade)); 
	 
//	 
//	 for(int i = 0; i <= quantidade; i++ ) {
//		 this.preco.add(produto.getPreco());
//	 }
	}
	
	public void setProduto(Produto produto) {
		this.produto = produto;
	}
	
	
	public long getId() {
		return this.id;
	}
	
	
	public BigDecimal getPreco() {
		return this.preco;
	}
	
	public Produto getProduto() {
		return this.produto;
	}
	
	public Integer getQuantidade() {
		return this.quantidade;
	}
	
	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}
	

	
	public void setId(long id) {
		this.id = id;
	}

	
	public void setPreco(BigDecimal preco) {
		this.preco = preco;
	}

	  @Override
	    public String toString() {
	        return "Item {" +
	        		"Nome=" + this.getProduto().getNome()+
	                ", numeroDeItens=" + this.getQuantidade() 
	                + "valorTotal=" + this.getPreco();
	    }	  
	  
}
