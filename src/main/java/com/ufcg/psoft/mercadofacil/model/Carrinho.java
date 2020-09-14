package com.ufcg.psoft.mercadofacil.model;

import java.math.BigDecimal;
import java.util.ArrayList;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;


import exceptions.ObjetoInvalidoException;


@Entity
public class Carrinho {

	@Id
	@GeneratedValue
    public static long ID_CARRINHO = 1;
	
	@OneToMany
    private ArrayList<ItemCarrinho> itens = new ArrayList<ItemCarrinho>();
	private int qtdItens;
	private BigDecimal valorTotal;



 
    public long getId() {
        return ID_CARRINHO;
    }


    public int getQtdItens() {
        return this.qtdItens;
    }
    
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

    public boolean adicionaProduto(Produto produto, int quantidade) throws ObjetoInvalidoException {
      int resp = produto.getSituacao();
      if(resp == Produto.DISPONIVEL) {
    	  ItemCarrinho novaAdicao = new ItemCarrinho(produto, quantidade);
    	 this.setItens(novaAdicao);
    	 this.addItens(novaAdicao.getQuantidade());
    	 this.adicionaPreco(novaAdicao.getPreco());
    	 return true;
       } else {
    	   return false;
       }
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
