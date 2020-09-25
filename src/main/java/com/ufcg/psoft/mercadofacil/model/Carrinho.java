package com.ufcg.psoft.mercadofacil.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;



import org.springframework.stereotype.Component;


@Component
public class Carrinho {

    private List<ItemCarrinho> itens;
	private int qtdItens;
	private long contadorIds;
	
	
	public Carrinho(ArrayList<ItemCarrinho> itens, int qtdItens, BigDecimal valorTotal) {
		super();
		this.itens = new ArrayList<ItemCarrinho>();
		this.qtdItens = qtdItens;
		
	}

	public Carrinho() {
		super();
		this.qtdItens = 0;
		this.itens = new ArrayList<ItemCarrinho>();
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
    
    public List<ItemCarrinho> getItens() {
    	return itens;
    }
    
    public void setItens(ItemCarrinho item) {
		this.itens.add(item);
	}
    
    public void setQtdItens(int qtd) {
    	this.qtdItens = qtd;
    }

    public void adicionaProduto(Produto produto, int quantidade) {
  
    	 ItemCarrinho novaAdicao = new ItemCarrinho(produto, quantidade);
    	 this.setItens(novaAdicao);
    	 novaAdicao.setId(this.contadorIds += 1);
    	 this.addItens(novaAdicao.getQuantidade());
     }
    
    public boolean removeItem(long id) {
    	
    	for(ItemCarrinho item: this.itens) {
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
