package com.ufcg.psoft.mercadofacil.model;

import javax.persistence.Entity;


@Entity
public class ItemCarrinho extends Item {
	
	public ItemCarrinho(Produto produto, Integer quantidade) {
		super(produto, quantidade);
	}

	
}
