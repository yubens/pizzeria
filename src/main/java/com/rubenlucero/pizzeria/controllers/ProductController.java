package com.rubenlucero.pizzeria.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rubenlucero.pizzeria.models.entity.Product;
import com.rubenlucero.pizzeria.models.services.IProductService;

@RestController
@RequestMapping("/api")
public class ProductController {
	@Autowired
	IProductService productService;
	
	@GetMapping("/products")
	public List<Product> index() {
		return productService.findAll();
	}
	
	@GetMapping("/products/{id}")
	public ResponseEntity<?> show(@PathVariable Long id) {
		Product product = null;
		Map<String, Object> response = new HashMap<>();
		
		product = productService.findById(id);
		
		if (product == null) {
			response.put("error", "product not found");
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}			
		
		return new ResponseEntity<>(product, HttpStatus.OK) ;
	}
	
	@PostMapping("/products")	
	public ResponseEntity<?> create(@Valid @RequestBody Product product, BindingResult result) {
		Map<String, Object> response = new HashMap<>();
		
		if (result.hasErrors()) {
			List<String> errors = result.getFieldErrors()
					.stream()
					.map(err -> "Campo '" + err.getField() + "' " + err.getDefaultMessage())
					.collect(Collectors.toList());
			
			response.put("errors", errors);
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}
		
		Product productSaved = productService.save(product);
		
		if (productSaved == null) {
			response.put("error", "product not created");
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<>(product, HttpStatus.CREATED);
	}
	
	@PutMapping("/products/{id}")	
	public ResponseEntity<?> update(@Valid @RequestBody Product product, BindingResult result, @PathVariable Long id) {
		Product productResponse = null;
		Map<String, Object> response = new HashMap<>();
		
		if (result.hasErrors()) {
			List<String> errors = result.getFieldErrors()
					.stream()
					.map(err -> "Campo '" + err.getField() + "' " + err.getDefaultMessage())
					.collect(Collectors.toList());
			
			response.put("errors", errors);
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}
		
		productResponse = productService.update(id, product);
		
		if (productResponse == null) {
			response.put("error", "cannot update, product not found");
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<>(productResponse, HttpStatus.OK) ;
	}
	
	@DeleteMapping("/products/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		ResponseEntity<?> responseEntity = productService.delete(id);
		
		return responseEntity;
	}
}
