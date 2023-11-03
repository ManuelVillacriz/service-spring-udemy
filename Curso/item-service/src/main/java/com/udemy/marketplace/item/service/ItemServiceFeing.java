package com.udemy.marketplace.item.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.udemy.marketplace.commons.entity.Product;
import com.udemy.marketplace.item.clients.ProductClient;
import com.udemy.marketplace.item.model.Item;

@Service("itemServiceFeing")
@Primary
public class ItemServiceFeing implements ItemService{

	@Autowired
	private ProductClient productClient;
	
	@Override
	public List<Item> findAll() {
		return productClient.findAll().stream().map(p -> new Item(p,2)).collect(Collectors.toList());
	}

	@Override
	public Item findById(Long id, Integer amount) {
		return new Item(productClient.findById(id),amount);
	}

	@Override
	public Product create(Product product) {
		return productClient.create(product);
	}

	@Override
	public Product edit(Product product, Long id) {
		return productClient.edit(product, id);
	}

	@Override
	public void delete(Long id) {
		productClient.delete(id);
	}

}
