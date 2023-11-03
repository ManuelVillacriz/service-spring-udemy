package com.udemy.marketplace.item.model;

import com.udemy.marketplace.commons.entity.Product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Item {
	
	private Product product;
	private Integer amount;
	
	
	public Double getTotal() {
		return product.getPrice() * amount;
	}
}
