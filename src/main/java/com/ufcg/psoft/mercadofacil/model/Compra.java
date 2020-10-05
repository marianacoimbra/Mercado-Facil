package com.ufcg.psoft.mercadofacil.model;

import java.math.BigDecimal;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.ufcg.psoft.mercadofacil.model.Usuarios.Usuario;
import com.ufcg.psoft.mercadofacil.model.pagamentos.Boleto;
import com.ufcg.psoft.mercadofacil.model.pagamentos.Credito;
import com.ufcg.psoft.mercadofacil.model.pagamentos.Paypal;
import com.ufcg.psoft.mercadofacil.model.pagamentos.TipoPagamento;

@Entity
public class Compra {

	@Id
	@GeneratedValue
	private Long id;

	@OneToMany
	private List<Item> itensCarrinho;
	private String data;
	private BigDecimal valorTotal;
	private String tipoPagamento;
	@OneToOne
	private Usuario usuario;

	public Compra() {
	}

	public Compra(List<Item> itens, String formaPagamento, String data, Usuario usuario) {

		this.itensCarrinho = itens;
		this.valorTotal = somaValorTotal();
		this.data = data;
		this.tipoPagamento = this.setFormaDePagamento(formaPagamento);
		this.usuario = usuario;
	}

	public String setFormaDePagamento(String forma) {
		if (forma.equals("BOLETO")) {
			TipoPagamento boleto = new Boleto();
			this.valorTotal.add(boleto.getAcrescimo(this.valorTotal));
			return boleto.getTipoPagamento();
		} else if (forma.equals("PAYPAL")) {
			TipoPagamento paypal = new Paypal();
			this.valorTotal.add(paypal.getAcrescimo(this.valorTotal));
			return paypal.getTipoPagamento();
		} else {
			TipoPagamento credito = new Credito();
			this.valorTotal.add(credito.getAcrescimo(this.valorTotal));
			return credito.getTipoPagamento();
		}
	}

	private BigDecimal somaValorTotal() {
		BigDecimal novoValor = new BigDecimal(0);

		for (Item item : this.itensCarrinho) {
			novoValor.add(item.getPreco());
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
	
	public void setValorTotal(BigDecimal valorTotal) {
		this.valorTotal = valorTotal;
	}

	public String getTipoPagamento() {
		return this.tipoPagamento;
	}

	public List<Item> getItens() {
		return this.itensCarrinho;
	}

	public String gerarDescritivo() {
		return this.getItens() + " " + this.getData() + " " + this.getTipoPagamento() + " " + this.getValorTotal() + " "
				+ this.usuario.getDesconto();

	}

	public String getUsuario() {
		return this.usuario.getNome();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Compra other = (Compra) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Carrinho{" + "Data= " + " " + this.getData() + "Valor: " + this.getValorTotal()
				+ this.getTipoPagamento();
	}
}
