package com.udemy.marketplace.product.repository;

import org.springframework.data.repository.CrudRepository;

import com.udemy.marketplace.commons.entity.Product;

public interface ProductRepository extends CrudRepository<Product, Long>{

}
