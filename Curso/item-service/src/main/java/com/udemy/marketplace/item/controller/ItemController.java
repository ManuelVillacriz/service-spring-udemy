package com.udemy.marketplace.item.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.udemy.marketplace.commons.entity.Product;
import com.udemy.marketplace.item.model.Item;
import com.udemy.marketplace.item.service.ItemService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;

@RefreshScope
@RestController
@RequestMapping(value = "/items")
public class ItemController {
	
	private final Logger log = LoggerFactory.getLogger(ItemController.class);
	
	@Autowired
	private Environment environment;
	
	@Autowired
	private CircuitBreakerFactory circuitBreakerFactory;
	
	@Value("${configuracion.texto}")
	private String texto;
	
	@Autowired
	@Qualifier("itemServiceFeing")
	private ItemService itemService;
	
	@GetMapping(value = "/findAll")
	public List<Item>findAll(@RequestParam(name = "nombre", required = false)String nombre, @RequestHeader(name = "token-request", required = false) String token){
		System.out.println(nombre);
		System.out.println(token);
		return itemService.findAll();
	}
	
	@GetMapping(value = "/findById/{id}/amount/{amount}")
	public Item findById(@PathVariable Long id, @PathVariable Integer amount) {
		return circuitBreakerFactory.create("items").run(() -> itemService.findById(id, amount),
				e -> metodoAlternativo(id, amount,e));
	}
	
	@CircuitBreaker(name = "items", fallbackMethod = "metodoAlternativo")
	@GetMapping(value = "/findByIdAnotacion/{id}/amount/{amount}")
	public Item findByIdAnotacion(@PathVariable Long id, @PathVariable Integer amount) {
		return itemService.findById(id, amount);
	}
	
	@CircuitBreaker(name = "items",fallbackMethod = "metodoAlternativo2")
	@TimeLimiter(name = "items")
	@GetMapping(value = "/findByIdTimeLimeter/{id}/amount/{amount}")
	public CompletableFuture<Item> findByIdTimeLimeter(@PathVariable Long id, @PathVariable Integer amount) {
		return CompletableFuture.supplyAsync(() -> itemService.findById(id, amount));
	}
	
	public Item metodoAlternativo( Long id, Integer amount, Throwable e) {
		log.info(e.getMessage());
		Item item = new Item();
		Product product = new Product();
		
		item.setAmount(amount);
		product.setId(id);
		product.setName("none");
		product.setPrice(0.0);
		item.setProduct(product);
		return item;
	}
	
	public CompletableFuture<Item> metodoAlternativo2( Long id, Integer amount, Throwable e) {
		log.info(e.getMessage());
		Item item = new Item();
		Product product = new Product();
		
		item.setAmount(amount);
		product.setId(id);
		product.setName("none");
		product.setPrice(0.0);
		item.setProduct(product);
		return CompletableFuture.supplyAsync(() -> item);
	}
	
	@GetMapping("/obtener-config")
	public ResponseEntity<?> obtenerConfig(@Value("${server.port}") String port){
		log.info(texto);
		Map<String,String> json = new HashMap<>();
		json.put("texto", texto);
		json.put("port", port);
		
		if(environment.getActiveProfiles().length >0 && environment.getActiveProfiles()[0].equals("dev")) {
			json.put("autor.nombre", environment.getProperty("configuracion.autor.nombre"));
			json.put("autor.email", environment.getProperty("configuracion.autor.email"));
		}
		return new ResponseEntity<Map<String,String>>(json, HttpStatus.OK);
	}
	
	@PostMapping("/create")
	@ResponseStatus(HttpStatus.CREATED)
	public Product create(@RequestBody Product product) {
		return itemService.create(product);
	}
	
	@PutMapping("/edit/{id}")
	@ResponseStatus(HttpStatus.CREATED)
	public Product edit(@RequestBody Product product, @PathVariable Long id) {				
		return itemService.edit(product,id);
	}
	
	@DeleteMapping("/delete/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long id) {
		itemService.delete(id);		
	}

}
