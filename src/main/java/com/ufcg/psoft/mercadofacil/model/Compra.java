package com.ufcg.psoft.mercadofacil.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.time.format.DateTimeFormatter;  
import java.time.LocalDateTime;    
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;


@Entity
public class Compra{

	@Id
	@GeneratedValue
    private long id;
	
	private ArrayList<ItemCompra> itensCompra;
	private String data;
	private BigDecimal valorTotal;
	@Enumerated(value=EnumType.STRING)
	private TipoPagamento tipoPagamento;
	
	  
	public Compra() {
	}
	
	public Compra(Carrinho carrinho, TipoPagamento tipoPagamento) {
	
	List<ItemCarrinho> itensCarrinho = carrinho.getItens();
		
	itensCompra = new ArrayList<ItemCompra>();
	
	for (ItemCarrinho item: itensCarrinho) {
		ItemCompra itemCompra = new ItemCompra(item.getProduto(), item.getQuantidade());
		this.itensCompra.add(itemCompra);
	}
	
	//this.valorTotal = carrinho.getValorTotal();
		
	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
	LocalDateTime date = LocalDateTime.now(); 
	this.data =  dtf.format(date);
	this.tipoPagamento = tipoPagamento;
	}
 
    public long getId() {
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
   
   public String gerarDescritivo() {
	   String descritivo = "";
	   
	   for(ItemCompra item: itensCompra) {
		   descritivo += item.toString();
	   }
	   
	   descritivo += "Data: " + this.getData() + "Valor total: " + this.getValorTotal() + 
			"Tipo de Pagamento: " + this.getTipoPagamento().toString();
	   
	   return descritivo;
	   
   }
   
    @Override
    public String toString() {
        return "Carrinho{" +                 
                "Data= " + this.getData(); 
    }
}
