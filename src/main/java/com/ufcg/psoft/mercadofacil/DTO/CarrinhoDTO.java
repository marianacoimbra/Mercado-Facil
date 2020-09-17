package com.ufcg.psoft.mercadofacil.DTO;

import java.math.BigDecimal;
import java.util.ArrayList;

import com.ufcg.psoft.mercadofacil.model.ItemCarrinho;

public class CarrinhoDTO {

    private int qtdItens;
    
    private ArrayList<ItemCarrinho> itens;
    
    private BigDecimal valorTotal;

    public CarrinhoDTO() {
    }

    public CarrinhoDTO(int qtdItens, BigDecimal valorTotal, ArrayList<ItemCarrinho> itens) {
        super();
        this.qtdItens = qtdItens;
        this.valorTotal = valorTotal;
        this.itens.addAll(itens);
    }

	public int getQtdItens() {
		return qtdItens;
	}

	public void setQtdItens(int qtdItens) {
		this.qtdItens = qtdItens;
	}

	public ArrayList<ItemCarrinho> getItens() {
		return itens;
	}

	public void setItens(ArrayList<ItemCarrinho> itens) {
		this.itens = itens;
	}

	public BigDecimal getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(BigDecimal valorTotal) {
		this.valorTotal = valorTotal;
	}

    
}