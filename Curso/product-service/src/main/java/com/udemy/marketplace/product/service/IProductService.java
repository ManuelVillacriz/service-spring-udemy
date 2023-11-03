package com.udemy.marketplace.product.service;

import java.util.List;

import com.udemy.marketplace.commons.entity.Product;

public interface IProductService {
	
	public List<Product> findAll();
	public Product findById(Long id);
	public Product save(Product product);
	public void deleteById(Long id);

}
