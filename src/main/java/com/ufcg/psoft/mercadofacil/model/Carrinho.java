package com.ufcg.psoft.mercadofacil.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.OneToMany;

import org.springframework.stereotype.Component;


@Component
public class Carrinho {

	@OneToMany
    private List<Item> itens;
	private int qtdItens;
	private long contadorIds;
	
	
	public Carrinho(ArrayList<Item> itens, int qtdItens, BigDecimal valorTotal) {
		super();
		this.itens = new ArrayList<Item>();
		this.qtdItens = qtdItens;
		
	}

	public Carrinho() {
		super();
		this.qtdItens = 0;
		this.itens = new ArrayList<Item>();
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
    
    public List<Item> getItens() {
    	return itens;
    }
    
    public void setItens(Item item) {
		this.itens.add(item);
	}
    
    public void setQtdItens(int qtd) {
    	this.qtdItens = qtd;
    }

    public void adicionaProduto(Produto produto, int quantidade) {
  
    	 Item novaAdicao = new Item(produto, quantidade);
    	 this.setItens(novaAdicao);
    	 novaAdicao.setId(this.contadorIds += 1);
    	 this.addItens(novaAdicao.getQuantidade());
     }
    
    public boolean removeItem(Long id) {
    	
    	for(Item item: this.itens) {
			if(id == item.getId()){
			return this.itens.remove(item);
			} 
		}
		return false;
    }
   
    

    public void esvaziarCarrinho() {
    	this.itens.clear();
    }
    

    @Override
    public String toString() {
        return "Carrinho: " + 
                "item: " + this.getItens() +
               ", numeroDeItens: " + this.getQtdItens();
    }
    
}
