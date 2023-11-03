package com.udemy.marketplace.product.controller;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.udemy.marketplace.commons.entity.Product;
import com.udemy.marketplace.product.service.IProductService;

@RestController
@RequestMapping(value = "/products")
public class ProductController {

	@Autowired
	private Environment env;

	@Autowired
	private IProductService productService;

	@GetMapping(value = "/findAll")
	public List<Product> findAll() {
		// return productService.findAll();
		return productService.findAll().stream().map(product -> {
			product.setPort(Integer.parseInt(env.getProperty("local.server.port")));
			// producto.setPort(port);
			return product;
		}).collect(Collectors.toList());
	}

	@GetMapping(value = "/findById/{id}")
	public Product findById(@PathVariable Long id) throws InterruptedException {

		if (id.equals(2L)) {
			throw new IllegalStateException("producto no encontrado");
		}

		if (id.equals(1L)) {
			TimeUnit.SECONDS.sleep(5L);
		}

		Product product = productService.findById(id);
		product.setPort(Integer.parseInt(env.getProperty("local.server.port")));

//		try {
//			Thread.sleep(2000L);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}

		return productService.findById(id);
	}

	@PostMapping("/create")
	@ResponseStatus(HttpStatus.CREATED)
	public Product create(@RequestBody Product product) {
		return productService.save(product);
	}

	@PutMapping("/edit/{id}")
	@ResponseStatus(HttpStatus.CREATED)
	public Product edit(@RequestBody Product product, @PathVariable Long id) {
		Product productDb = productService.findById(id);

		productDb.setName(product.getName());
		productDb.setPrice(product.getPrice());

		return productService.save(productDb);
	}

	@DeleteMapping("/delete/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long id) {
		productService.deleteById(id);
	}

}
