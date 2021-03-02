package com.ufcg.psoft.mercadofacil.model.pagamentos;

import java.math.BigDecimal;

import javax.persistence.GeneratedValue;

import org.springframework.data.annotation.Id;
import org.springframework.stereotype.Component;

@Component
public class Paypal extends TipoPagamento {
	@Id
	@GeneratedValue
	private Long id;

	public Paypal() {
		super();
	}

	@Override
	public BigDecimal getAcrescimo(BigDecimal valorCompra) {
		BigDecimal porcent = new BigDecimal(0.02);
		BigDecimal newValue = valorCompra.multiply(porcent);

		this.acrescimo.add(newValue);

		return this.acrescimo;
	}

	@Override
	public String getTipoPagamento() {
		return "PAYPAL";
	}

}
