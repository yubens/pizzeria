package com.rubenlucero.pizzeria.models.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rubenlucero.pizzeria.models.dao.IProductDao;
import com.rubenlucero.pizzeria.models.entity.Product;

@Service
public class ProductService implements IProductService {
	@Autowired
	IProductDao productDao;

	@Override
	@Transactional(readOnly = true)
	public List<Product> findAll() {
		return (List<Product>) productDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Product findById(Long id) {
		return productDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public Product save(Product product) {
		return productDao.save(product);
	}

	@Transactional
	@Override
	public ResponseEntity<?> delete(Long id) {
		Map<String, Object> response = new HashMap<>();
		Product product = null;
		
		try {
			product = findById(id);
			
			if (product != null)
				productDao.deleteById(id);
			else {
				response.put("error", "product not found");
				return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
			}
						
		}  catch (Exception e) {
			response.put("error", "cannot delete product");
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("message", "product deleted");	
		return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
	}

	@Override
	public Product update(Long id, Product product) {
		Product actualProduct = findById(id);
		
		if (actualProduct != null) {
			actualProduct.setLongDescription(product.getLongDescription());
			actualProduct.setShortDescription(product.getShortDescription());
			actualProduct.setName(product.getName());
			actualProduct.setUnitPrice(product.getUnitPrice());
			
			return save(actualProduct);
		}		
		
		return null;
	}

}
