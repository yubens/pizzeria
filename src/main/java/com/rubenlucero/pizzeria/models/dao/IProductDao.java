package com.rubenlucero.pizzeria.models.dao;

import org.springframework.data.repository.CrudRepository;

import com.rubenlucero.pizzeria.models.entity.Product;

public interface IProductDao extends CrudRepository<Product, Long> {

}
