package com.rubenlucero.pizzeria.models.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.rubenlucero.pizzeria.models.entity.OrderHeader;

public interface IOrderDao extends CrudRepository<OrderHeader, Long> {
	public List<OrderHeader> findByCreatedAt(Date createdAt);
}
