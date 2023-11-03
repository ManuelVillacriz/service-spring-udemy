package com.udemy.marketplace.item.clients;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.udemy.marketplace.commons.entity.Product;



@FeignClient(name = "product-service")
public interface ProductClient {
	
	@GetMapping(value = "/products/findAll")
	public List<Product> findAll();
	
	@GetMapping(value = "/products/findById/{id}")
	public Product findById(@PathVariable Long id);
	
	@PostMapping("/products/create")
	public Product create(@RequestBody Product product);
	
	@PutMapping("/products/edit/{id}")
	public Product edit(@RequestBody Product product, @PathVariable Long id);
	
	@DeleteMapping("/products/delete/{id}")
	public void delete(@PathVariable Long id);

}
