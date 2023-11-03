package com.udemy.marketplace.item.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.udemy.marketplace.commons.entity.Product;
import com.udemy.marketplace.item.model.Item;

@Service
public class ItemServiceImpl implements ItemService {

	@Autowired
	private RestTemplate restTemplate;

	@Override
	public List<Item> findAll() {
		List<Product> products = Arrays.asList(restTemplate.getForObject("http://localhost:8091/findAll", Product[].class));
		return products.stream().map(p -> new Item(p, 2)).collect(Collectors.toList());
	}

	@Override
	public Item findById(Long id, Integer amount) {
		Map<String, String> pathVariables = new HashMap<String, String>();
		pathVariables.put("id", id.toString());
		Product product = restTemplate.getForObject("http://localhost:8091/findById/{id}", Product.class,
				pathVariables);
		return new Item(product, amount);
	}

	@Override
	public Product create(Product product) {
		HttpEntity<Product> body= new HttpEntity<Product>(product);
		ResponseEntity<Product> response = restTemplate.exchange("http://product-service/create/{id}", HttpMethod.POST, body, Product.class);
		Product productResponse = response.getBody();
		return productResponse;
	}

	@Override
	public Product edit(Product product, Long id) {
		HttpEntity<Product> body= new HttpEntity<Product>(product);
		Map<String, String> pathVariables = new HashMap<String, String>();
		pathVariables.put("id", id.toString());
		ResponseEntity<Product> response = restTemplate.exchange("http://product-service/edit/{id}", HttpMethod.PUT, body, Product.class,pathVariables);
		return response.getBody();
	}

	@Override
	public void delete(Long id) {
		Map<String, String> pathVariables = new HashMap<String, String>();
		pathVariables.put("id", id.toString());
		restTemplate.delete("http://product-service/delete/{id}", pathVariables);
	}

}
