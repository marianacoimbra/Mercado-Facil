package com.ufcg.psoft.mercadofacil.DTO;

import java.math.BigDecimal;

import com.ufcg.psoft.mercadofacil.model.Carrinho;
import com.ufcg.psoft.mercadofacil.model.Compra;

public class CompraDTO {

	Compra compra;

	public CompraDTO() {
		super();
	}

	
	private CompraDTO(Compra compra) {
		super();
		this.compra = compra;
	}
	

    public String getData() {
    	return this.compra.getData();
    }
    
   public BigDecimal getValorTotal() {
	   return this.compra.getValorTotal();
   }
   

	public String gerarDescritivo() {
		return this.compra.gerarDescritivo();
	}

}
