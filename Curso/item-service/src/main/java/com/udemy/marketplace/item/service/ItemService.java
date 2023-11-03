package com.udemy.marketplace.item.service;

import java.util.List;

import com.udemy.marketplace.commons.entity.Product;
import com.udemy.marketplace.item.model.Item;

public interface ItemService {
	public List<Item> findAll();

	public Item findById(Long id, Integer amount);
	
	public Product create(Product product);
	
	public Product edit(Product product, Long id);
	
	public void delete(Long id);

}
