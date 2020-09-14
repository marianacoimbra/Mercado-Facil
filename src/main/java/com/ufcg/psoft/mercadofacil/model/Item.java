package com.ufcg.psoft.mercadofacil.model;

import java.math.BigDecimal;

public abstract class Item {
	private Produto produto;
	private Integer quantidade;
	private BigDecimal preco;
	
	
	
	public Item(Produto produto, Integer quantidade) {
	 this.produto = produto;
	 this.quantidade = quantidade;
	 preco = produto.getPreco().multiply(BigDecimal.valueOf(quantidade));

	}
	
	public void setProduto(Produto produto) {
		this.produto = produto;
	}
	
	public Integer getQuantidade() {
		return this.quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}
	
	public Produto getProduto() {
		return this.produto;
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
