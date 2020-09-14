package com.ufcg.psoft.mercadofacil.model;

import java.math.BigDecimal;
import java.util.ArrayList;

import java.time.format.DateTimeFormatter;  
import java.time.LocalDateTime;    
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;


@Entity
public class Compra{

	@Id
	@GeneratedValue
    private long id;
	
	@OneToOne
    private Carrinho carrinho;
	@OneToOne
	private ArrayList<ItemCompra> itensCompra;
	private String data;
	private BigDecimal valorTotal;
	
	
	public Compra() {
		
	}
	
	public Compra(ArrayList<ItemCarrinho> itensCarrinho) {
	itensCompra = new ArrayList<ItemCompra>();
	for (ItemCarrinho item: itensCarrinho) {
		ItemCompra itemCompra = new ItemCompra(item.getProduto(), item.getQuantidade());
		this.itensCompra.add(itemCompra);
	}
	
	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
	LocalDateTime date = LocalDateTime.now(); 
	this.data =  dtf.format(date);
	}
 
//    public long getId() {
//        return id;
//    }
//
//    public void setId(long id) {
//        this.id = id;
//    }

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
	   return this.toString();
   }
   
   
    @Override
    public String toString() {
        return "Carrinho{" +
                "id=" + id +
                 " " + this.getCarrinho().toString() +
                "Data= " + this.getData(); 
    }
}
