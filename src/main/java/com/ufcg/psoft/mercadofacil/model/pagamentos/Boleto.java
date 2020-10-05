package com.ufcg.psoft.mercadofacil.model.pagamentos;

import java.math.BigDecimal;

import javax.persistence.GeneratedValue;

import org.springframework.data.annotation.Id;
import org.springframework.stereotype.Component;

@Component
public class Boleto extends TipoPagamento {
	@Id
	@GeneratedValue
	private Long id;

	public Boleto() {
		super();
	}

	@Override
	public BigDecimal getAcrescimo(BigDecimal valorCompra) {
		return this.acrescimo;
	}

	@Override
	public String getTipoPagamento() {
		return "BOLETO";
	}

}
