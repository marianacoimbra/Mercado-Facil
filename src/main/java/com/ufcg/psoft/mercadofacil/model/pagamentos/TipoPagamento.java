package com.ufcg.psoft.mercadofacil.model.pagamentos;

import java.math.BigDecimal;

public abstract class TipoPagamento {

	protected BigDecimal acrescimo;

	public TipoPagamento() {
		this.acrescimo = new BigDecimal(0);

	}

	public abstract BigDecimal getAcrescimo(BigDecimal valorCompra);

	public abstract String getTipoPagamento();

}
