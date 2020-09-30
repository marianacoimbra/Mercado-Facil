package com.ufcg.psoft.mercadofacil.controller;

import java.awt.color.CMMException;
import java.math.BigDecimal;
import java.net.ResponseCache;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.ufcg.psoft.mercadofacil.DTO.LoteDTO;
import com.ufcg.psoft.mercadofacil.model.Lote;
import com.ufcg.psoft.mercadofacil.model.Produto;
import com.ufcg.psoft.mercadofacil.model.TipoPagamento;
import com.ufcg.psoft.mercadofacil.model.Compra;
import com.ufcg.psoft.mercadofacil.model.Item;
import com.ufcg.psoft.mercadofacil.model.Carrinho;
import com.ufcg.psoft.mercadofacil.repositories.CompraRepository;
import com.ufcg.psoft.mercadofacil.repositories.LoteRepository;
import com.ufcg.psoft.mercadofacil.repositories.ProdutoRepository;
import com.ufcg.psoft.mercadofacil.util.CustomErrorType;
import com.ufcg.psoft.mercadofacil.DTO.CarrinhoDTO;
import com.ufcg.psoft.mercadofacil.DTO.CompraDTO;
import com.ufcg.psoft.mercadofacil.DTO.ItemCarrinhoDTO;

import exceptions.ObjetoInvalidoException;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class RestApiController {

	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private LoteRepository loteRepository;
	
	@Autowired
	private CompraRepository compraRepository;

	@Autowired
	private Carrinho carrinho = new Carrinho();
	
		
	
	/*
	 * CARRINHO
	 */
	
	//Adiciona Produto no Carrinho por ID

	@PutMapping("/carrinho")
	public ResponseEntity<?> adicionaProduto(@RequestBody long id, int quantidade) {
		
		Optional<Produto> optionalProduto = produtoRepository.findById(id);
		
		
		if (!optionalProduto.isPresent()) {
			return new ResponseEntity<CustomErrorType>(new CustomErrorType("Produto with id " + id + " not found"),
					HttpStatus.NOT_FOUND);
		}
		
		Produto produto = optionalProduto.get();
		
		try {
			if (produto.getSituacao() == Produto.DISPONIVEL)
				carrinho.adicionaProduto(produto, quantidade);
		} catch (ObjetoInvalidoException e) {
			e.printStackTrace();
		}
		return new ResponseEntity<List<Item>>(carrinho.getItens(), HttpStatus.OK);
	}
	
	//Lista o carrinho 
	
		@RequestMapping(value = "/carrinho", method = RequestMethod.GET)
		public ResponseEntity<?> listarProdutosCarrinho() {
			
			if (this.carrinho.getItens().isEmpty()) {
				return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
			}
		
			
			return new ResponseEntity<List<Item>>(this.carrinho.getItens(), HttpStatus.OK);
		}
		
		
		//Exclui Itens do carrinho
		
		@RequestMapping(value = "/carrinho/{id}", method = RequestMethod.DELETE)
		public ResponseEntity<?> excluirItemCarrinho(@RequestBody long id) {
			
			boolean resp = this.carrinho.removeItem(id);
			 
			 if(resp == false) {
				 return new ResponseEntity<CustomErrorType>(new CustomErrorType("Produto with id " + id + " not found"),
							HttpStatus.NOT_FOUND);
			 }
			
			ItemCarrinhoDTO itemCarrinhoDTO = new ItemCarrinhoDTO(this.carrinho);
			
			return new ResponseEntity<String>(itemCarrinhoDTO.getItensCarrinho(), HttpStatus.OK);
		
		}
		
		
		//Descarta o Carrinho
		
		
		@RequestMapping(value = "/carrinho/", method = RequestMethod.DELETE)
		public ResponseEntity<?> descartarCarrinho() {

			this.carrinho.esvaziarCarrinho();
			this.carrinho.setQtdItens(0);
			
			String response = "Carrinho descartado com sucesso!";
			return new ResponseEntity<String>(response, HttpStatus.OK);
		
		}
		

	
	

	/*
	 * COMPRA
	 */
	
	
	//Faz uma compra
	
	@PostMapping("/compra")
	public ResponseEntity<?> compra(@RequestBody List<Item> itens, TipoPagamento tipoPagamento, String data ) {
		
		Compra compra = new Compra(itens, tipoPagamento, data);
		
		compraRepository.save(compra);
		this.carrinho.esvaziarCarrinho();
		this.carrinho.setQtdItens(0);
		return new ResponseEntity<String>(compra.gerarDescritivo(), HttpStatus.CREATED);
	}
	
	
	//Lista as compras
	
	@GetMapping("/compra")
	public ResponseEntity<?> listarCompras() {
		
		List<Compra> compras = compraRepository.findAll();

		if (compras.isEmpty()) {
			return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
		}
		
		return new ResponseEntity<List<Compra>>(compras, HttpStatus.OK);
	}

	
	//Busca Compra pelo ID
	
	@RequestMapping(value = "/compra/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getCompra(@PathVariable("id") long id) {

		Optional<Compra> optionalCompra = compraRepository.findById(id);
		
		if (!optionalCompra.isPresent()) {
			return new ResponseEntity<CustomErrorType>(new CustomErrorType("Produto with id " + id + " not found"),
					HttpStatus.NOT_FOUND);
		} 
		
		Compra compra = optionalCompra.get();
		
		CompraDTO compraDTO = new CompraDTO(compra);
		
		String response = compraDTO.gerarDescritivo();
		
		return new ResponseEntity<String>(response, HttpStatus.OK);
	}
	
	
		
	/*
	 * PRODUTO
	 */
	
	
	@RequestMapping(value = "/produtos", method = RequestMethod.GET)
	public ResponseEntity<?> listarProdutos() {
		List<Produto> produtos = new ArrayList<>();
		produtos = produtoRepository.findAll();
		
		if (produtos.isEmpty()) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
			// Outro código de erro pode ser retornado HttpStatus.NOT_FOUND
		}
		
		return new ResponseEntity<List<Produto>>(produtos, HttpStatus.OK);
	}

	
	@RequestMapping(value = "/produto/", method = RequestMethod.POST)
	public ResponseEntity<?> criarProduto(@RequestBody Produto produto, UriComponentsBuilder ucBuilder) {

		List<Produto> produtos = produtoRepository.findByCodigoBarra(produto.getCodigoBarra());
		
		// Produto não encontrado
		if (!produtos.isEmpty()) {
			return new ResponseEntity<CustomErrorType>(new CustomErrorType("O produto " + produto.getNome() + " do fabricante "
					+ produto.getFabricante() + " ja esta cadastrado!"), HttpStatus.CONFLICT);
		}

		try {
			produto.mudaSituacao(Produto.INDISPONIVEL);
		} catch (ObjetoInvalidoException e) {
			return new ResponseEntity<CustomErrorType>(new CustomErrorType("Error: Produto" + produto.getNome() + " do fabricante "
					+ produto.getFabricante() + " alguma coisa errada aconteceu!"), HttpStatus.NOT_ACCEPTABLE);
		}

		produtoRepository.save(produto);
		return new ResponseEntity<Produto>(produto, HttpStatus.CREATED);
	}
	
	
	
	@RequestMapping(value = "/produto/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> consultarProduto(@PathVariable("id") long id) {

		Optional<Produto> produto = produtoRepository.findById(id);
	

		if (!produto.isPresent()) {
			return new ResponseEntity<CustomErrorType>(new CustomErrorType("Produto with id " + id + " not found"),
					HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<Produto>(produto.get(), HttpStatus.OK);
	}
	
	
	
	
	@RequestMapping(value = "/produto/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> updateProduto(@PathVariable("id") long id, @RequestBody Produto produto) {

		Optional<Produto> optionalProduto = produtoRepository.findById(id);
		

		if (!optionalProduto.isPresent()) {
			return new ResponseEntity<CustomErrorType>(new CustomErrorType("Produto with id " + id + " not found"),
					HttpStatus.NOT_FOUND);
		}
		
		Produto currentProduto = optionalProduto.get();
		
		currentProduto.setNome(produto.getNome());
		currentProduto.setPreco(produto.getPreco());
		currentProduto.setCodigoBarra(produto.getCodigoBarra());
		currentProduto.mudaFabricante(produto.getFabricante());
		currentProduto.mudaCategoria(produto.getCategoria());
		currentProduto.setDescricao(produto.getDescricao());

		produtoRepository.save(currentProduto);
		
		return new ResponseEntity<Produto>(currentProduto, HttpStatus.OK);
	}
	

	
	@RequestMapping(value = "/produto/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteProduto(@PathVariable("id") long id) {

		Optional<Produto> optionalProduto = produtoRepository.findById(id);
		
		if (!optionalProduto.isPresent()) {
			return new ResponseEntity<CustomErrorType>(new CustomErrorType("Produto with id " + id + " not found"),
					HttpStatus.NOT_FOUND);
		}
				
		produtoRepository.delete(optionalProduto.get());

		return new ResponseEntity<Produto>(HttpStatus.NO_CONTENT);
	}
	
	
	
	/*
	 * Lotes
	 */
	
	@RequestMapping(value = "/lotes", method = RequestMethod.GET)
	public ResponseEntity<?> listarLotes() {
		
		List<Lote> lotes = loteRepository.findAll();

		if (lotes.isEmpty()) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
			// Outro código de erro pode ser retornado HttpStatus.NOT_FOUND
		}
		
		return new ResponseEntity<List<Lote>>(lotes, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/produto/{id}/lote", method = RequestMethod.POST)
	public ResponseEntity<?> criarLote(@PathVariable("id") long id, @RequestBody LoteDTO loteDTO) {
		
		Optional<Produto> optionalProduto = produtoRepository.findById(id);
		
		if (!optionalProduto.isPresent()) {
			return new ResponseEntity<CustomErrorType>(new CustomErrorType("Produto with id " + id + " not found"),
					HttpStatus.NOT_FOUND);
		}
		
		Produto product = optionalProduto.get();
		Lote lote = new Lote(product, loteDTO.getNumeroDeItens(), loteDTO.getDataDeValidade());
		
		loteRepository.save(lote);
		
		try {
			if (product.getSituacao() == Produto.INDISPONIVEL) {
				if (loteDTO.getNumeroDeItens() > 0) {
					Produto produtoDisponivel = product;
					produtoDisponivel.situacao = Produto.DISPONIVEL;
					produtoRepository.save(produtoDisponivel);
				}
			}
		} catch (ObjetoInvalidoException e) {
			e.printStackTrace();
		}

		return new ResponseEntity<>(lote, HttpStatus.CREATED);
	}

}