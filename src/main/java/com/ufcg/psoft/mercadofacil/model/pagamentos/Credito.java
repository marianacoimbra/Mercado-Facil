package com.ufcg.psoft.mercadofacil.model.pagamentos;

import java.math.BigDecimal;

import javax.persistence.GeneratedValue;

import org.springframework.data.annotation.Id;
import org.springframework.stereotype.Component;

@Component
public class Credito extends TipoPagamento {
	@Id
	@GeneratedValue
	private Long id;

	public Credito() {
		super();
	}

	@Override
	public BigDecimal getAcrescimo(BigDecimal valorCompra) {
		BigDecimal porcent = new BigDecimal(0.05);
		BigDecimal newValue = valorCompra.multiply(porcent);

		this.acrescimo.add(newValue);

		return this.acrescimo;

	}

	@Override
	public String getTipoPagamento() {
		return "CARTAO CREDITO";
	}

}
