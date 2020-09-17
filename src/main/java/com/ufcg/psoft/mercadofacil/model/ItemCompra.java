package com.ufcg.psoft.mercadofacil.model;

import javax.persistence.Entity;

@Entity
public class ItemCompra extends Item {

		public ItemCompra(Produto produto, Integer quantidade) {
			super(produto, quantidade);
		}
}
