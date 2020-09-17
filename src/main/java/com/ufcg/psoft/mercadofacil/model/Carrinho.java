package com.ufcg.psoft.mercadofacil.model;

import java.math.BigDecimal;
import java.util.ArrayList;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.springframework.stereotype.Component;

import exceptions.ObjetoInvalidoException;


@Component
public class Carrinho {

    private ArrayList<ItemCarrinho> itens = new ArrayList<ItemCarrinho>();
	private int qtdItens;
	private BigDecimal valorTotal;
	
	public Carrinho(ArrayList<ItemCarrinho> itens, int qtdItens, BigDecimal valorTotal) {
		super();
		this.itens = itens;
		this.qtdItens = qtdItens;
		this.valorTotal = valorTotal;
	}

	public Carrinho() {
		super();
	}

    public int getQtdItens() {
        return this.qtdItens;
    }
    
    /*Metodo chamado no funcao de adicionar
     *  Produto que aumenta a quantidade de produtos no carrinho
     */
    public void addItens(int qtd) {
    	this.qtdItens += qtd;
    }
    
    public ArrayList<ItemCarrinho> getItens() {
    	return itens;
    }
    
    public void adicionaPreco(BigDecimal preco) {
    	this.valorTotal = valorTotal.add(preco);
    }
    
    public void setItens(ItemCarrinho item) {
		this.itens.add(item);
	}

    public void adicionaProduto(Produto produto, int quantidade) throws ObjetoInvalidoException {
  
    	  ItemCarrinho novaAdicao = new ItemCarrinho(produto, quantidade);
    	 this.setItens(novaAdicao);
    	 this.addItens(novaAdicao.getQuantidade());
    	 this.adicionaPreco(novaAdicao.getPreco());
    	
    }
    
    public void  setValorTotal(BigDecimal valorTotal) {
		this.valorTotal = valorTotal;
	}
    
    public BigDecimal getValorTotal() {
    	return this.valorTotal;
    }
    

    @Override
    public String toString() {
        return "Carrinho{" +
                ", itens=" + this.getItens().toString() +
                ", numeroDeItens=" + this.getQtdItens() 
                + "valorTotal=" + this.getValorTotal();
    }


    
}
