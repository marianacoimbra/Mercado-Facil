package com.ufcg.psoft.mercadofacil.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.time.format.DateTimeFormatter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Compra {

	@Id
	@GeneratedValue
	private Long id;

	@OneToMany
	private List<Item> itensCarrinho;
//	private ArrayList<Item> itens;
	private String data;
	private BigDecimal valorTotal;
	@Enumerated(value = EnumType.STRING)
	private TipoPagamento tipoPagamento;

	public Compra() {
	}

	public Compra(List<Item> itens, TipoPagamento tipoPagamento, String data) {

		this.itensCarrinho = itens;
		this.valorTotal = somaValorTotal();
		this.data = data;
		this.tipoPagamento = tipoPagamento;
	}

	private BigDecimal somaValorTotal() {
		BigDecimal novoValor = new BigDecimal(0);

		for (Item item : this.itensCarrinho) {
			novoValor = novoValor.add(item.getPreco());
		}

		return novoValor;
	}

	public Long getId() {
		return id;
	}

	public String getData() {
		return this.data;
	}

	public BigDecimal getValorTotal() {
		return this.valorTotal;
	}

	public TipoPagamento getTipoPagamento() {
		return this.tipoPagamento;
	}

	public void setTipoPagamento(TipoPagamento tipoPagamento) {
		this.tipoPagamento = tipoPagamento;
	}

	public List<Item> getItens() {
		return this.itensCarrinho;
	}

	public String gerarDescritivo() {
		return this.getItens() + this.getData() + this.getTipoPagamento() + this.getValorTotal();

	}

	@Override
	public String toString() {
		return "Carrinho{" + "Data= " + this.getData();
	}
}
