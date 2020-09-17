package com.ufcg.psoft.mercadofacil.model;

import java.math.BigDecimal;
import java.util.ArrayList;

import java.time.format.DateTimeFormatter;  
import java.time.LocalDateTime;    
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;


@Entity
public class Compra{

	@Id
	@GeneratedValue
    private long id;
	

    private Carrinho carrinho;
	@OneToMany
	private ArrayList<ItemCompra> itensCompra;
	private String data;
	private BigDecimal valorTotal;
	
	
	public Compra() {
		
	}
	
	public Compra(Carrinho carrinho) {
	ArrayList<ItemCarrinho> itensCarrinho = carrinho.getItens();
	itensCompra = new ArrayList<ItemCompra>();
	for (ItemCarrinho item: itensCarrinho) {
		ItemCompra itemCompra = new ItemCompra(item.getProduto(), item.getQuantidade());
		this.itensCompra.add(itemCompra);
	}
	
	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
	LocalDateTime date = LocalDateTime.now(); 
	this.data =  dtf.format(date);
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
   
   public Carrinho getCarrinho() {
	   return this.carrinho;
   }
   
   public String gerarDescritivo() {
	   String descritivo = "";
	   
	   for(ItemCompra item: itensCompra) {
		   descritivo += item.toString();
	   }
	   
	   return descritivo;
	   
   }
   
   
    @Override
    public String toString() {
        return "Carrinho{" +
                 this.getCarrinho().toString() +
                "Data= " + this.getData(); 
    }
}
