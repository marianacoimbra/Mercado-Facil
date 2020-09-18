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
import com.ufcg.psoft.mercadofacil.model.Compra;
import com.ufcg.psoft.mercadofacil.model.Carrinho;
import com.ufcg.psoft.mercadofacil.model.ItemCompra;
import com.ufcg.psoft.mercadofacil.model.ItemCarrinho;
import com.ufcg.psoft.mercadofacil.repositories.CompraRepository;
import com.ufcg.psoft.mercadofacil.repositories.ItemCompraRepository;
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
	private ItemCompraRepository itemCompraRepository;
		
	@Autowired
	private Carrinho carrinho = new Carrinho();
	
		
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
	
	

	@PutMapping("/adicionaProdutoCarrinho")
	public ResponseEntity<?> adicionaProdutoPorNome(@RequestBody String nome, int quantidade) {
		
		Produto optionalProduto = produtoRepository.findByNome(nome);
		
		if (optionalProduto.equals(null)) {
			return new ResponseEntity<CustomErrorType>(new CustomErrorType("Produto with id " + nome + " not found"),
					HttpStatus.NOT_FOUND);
		}
		
		try {
			if (optionalProduto.getSituacao() == Produto.INDISPONIVEL)
				carrinho.adicionaProduto(optionalProduto, quantidade);
		} catch (ObjetoInvalidoException e) {
			e.printStackTrace();
		}
		
		return new ResponseEntity<List<ItemCarrinho>>(carrinho.getItens(), HttpStatus.OK);
	}
	
	
	@PostMapping("/compra")
	public ResponseEntity<?> compra(@RequestBody Carrinho carrinho) {
		
		Compra compra = new Compra(carrinho);
		CompraDTO compraDTO = new CompraDTO();
		String descritivo = compraDTO.gerarDescritivo();
		
		compraRepository.save(compra);
		this.carrinho.esvaziarCarrinho();
		this.carrinho.setQtdItens(0);
		return new ResponseEntity<String>(descritivo, HttpStatus.CREATED);
	}
	
	
	@RequestMapping(value = "/listaCarrinho", method = RequestMethod.GET)
	public ResponseEntity<?> listarProdutosCarrinho() {
		
		if (this.carrinho.getItens().isEmpty()) {
			return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
		}
		
		ItemCarrinhoDTO itemCarrinhoDTO= new ItemCarrinhoDTO(this.carrinho);
		
		return new ResponseEntity<String>(itemCarrinhoDTO.getItensCarrinho(), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/itemCarrinho/{name}", method = RequestMethod.DELETE)
	public ResponseEntity<?> excluirItemCarrinho(@PathVariable("name") String name) {

		ArrayList<ItemCarrinho> itens = this.carrinho.getItens();
		
		for(ItemCarrinho item: itens) {
			if(name.equals(item.getNomeItem())) {
				itens.remove(item);
				break;
			}
		}
		
		ItemCarrinhoDTO itemCarrinhoDTO= new ItemCarrinhoDTO(this.carrinho);
		
		return new ResponseEntity<String>(itemCarrinhoDTO.getItensCarrinho(), HttpStatus.OK);
	
	}
	
	
	@RequestMapping(value = "/descartaCarrinho/", method = RequestMethod.DELETE)
	public ResponseEntity<?> descartarCarrinho() {

		this.carrinho.esvaziarCarrinho();
		this.carrinho.setQtdItens(0);
		
		String response = "Carrinho descartado com sucesso!";
		return new ResponseEntity<String>(response, HttpStatus.OK);
	
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
	
	
	@RequestMapping(value = "/lotes", method = RequestMethod.GET)
	public ResponseEntity<?> listarLotes() {
		
		List<Lote> lotes = loteRepository.findAll();

		if (lotes.isEmpty()) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
			// Outro código de erro pode ser retornado HttpStatus.NOT_FOUND
		}
		
		return new ResponseEntity<List<Lote>>(lotes, HttpStatus.OK);
	}

}