package com.ufcg.psoft.mercadofacil.model;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;


@Entity
public abstract class Item {
	@OneToOne
	private Produto produto;
	private Integer quantidade;
	private BigDecimal preco;
	@Id
	@GeneratedValue
	private long id;
	
	
	
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
	
	public Produto getProduto() {
		return this.produto;
	}
	
	public Integer getQuantidade() {
		return this.quantidade;
	}
	
	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}
	
	
	public long getId() {
		return this.id;
	}
	
	public void setId(long id) {
		this.id = id;
	}

	
	public String getNomeItem() {
		return this.produto.getNome(); 
	}
	
	public BigDecimal getPreco() {
		return this.preco;
	}
	
	public void setPreco(BigDecimal preco) {
		this.preco = preco;
	}

	  @Override
	    public String toString() {
	        return "Item {" +
	        		"Nome=" + this.getNomeItem() +
	                ", numeroDeItens=" + this.getQuantidade() 
	                + "valorTotal=" + this.getPreco();
	    }	  
	  
}
