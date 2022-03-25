package com.rubenlucero.pizzeria.models.dao;

import org.springframework.data.repository.CrudRepository;

import com.rubenlucero.pizzeria.models.entity.OrderItem;

public interface IOrderItemDao extends CrudRepository<OrderItem, Long> {

}
