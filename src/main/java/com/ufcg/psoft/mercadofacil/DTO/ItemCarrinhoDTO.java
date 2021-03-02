package com.ufcg.psoft.mercadofacil.DTO;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.ufcg.psoft.mercadofacil.model.Carrinho;
import com.ufcg.psoft.mercadofacil.model.Item;

public class ItemCarrinhoDTO {
	private Item itemCarrinho;
	private Carrinho carrinho;


	public ItemCarrinhoDTO() {
		super();
	}

	
	public ItemCarrinhoDTO(Carrinho carrinho) {
		this.carrinho = carrinho;
	}
	
	
	public int getQuantidade() {
		return this.itemCarrinho.getQuantidade();
	}
	
	
	public Integer getItemCarrinho() {
		
		Integer itemCarrinhoString =  this.itemCarrinho.getQuantidade();
		
		return itemCarrinhoString;
	}
	
	public String getItensCarrinho() {
		List<Item> itensCarrinho = carrinho.getItens();
		
		String itensCarrinhoString = "";
		
		for(Item item: itensCarrinho) {
			itensCarrinhoString += item.toString();
		}
		
		return itensCarrinhoString;
	}
 	
}