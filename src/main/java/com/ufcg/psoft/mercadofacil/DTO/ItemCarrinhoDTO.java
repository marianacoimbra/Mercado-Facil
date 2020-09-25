package com.ufcg.psoft.mercadofacil.DTO;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.ufcg.psoft.mercadofacil.model.Carrinho;
import com.ufcg.psoft.mercadofacil.model.ItemCarrinho;;

public class ItemCarrinhoDTO {
	private ItemCarrinho itemCarrinho;
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
	
	
	public String getItemCarrinho() {
		
		String itemCarrinhoString = this.itemCarrinho.getNomeItem() + " " + this.itemCarrinho.getQuantidade();
		
		return itemCarrinhoString;
	}
	
	public String getItensCarrinho() {
		List<ItemCarrinho> itensCarrinho = carrinho.getItens();
		
		String itensCarrinhoString = "";
		
		for(ItemCarrinho item: itensCarrinho) {
			itensCarrinhoString += item.toString();
		}
		
		return itensCarrinhoString;
	}
 	
}