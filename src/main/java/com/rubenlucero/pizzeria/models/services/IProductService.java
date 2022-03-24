package com.rubenlucero.pizzeria.models.services;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.rubenlucero.pizzeria.models.entity.Product;

public interface IProductService {
	public List<Product> findAll();
	public Product findById(Long id);
	public Product save(Product product);
	public ResponseEntity<?> delete(Long id);
	public Product update(Long id, Product product);
}
