package com.rubenlucero.pizzeria.models.services;

import java.util.List;

import com.rubenlucero.pizzeria.models.dto.ResponseOrderDto;

public interface IOrderService {
	public List<ResponseOrderDto> findAll();
	public List<ResponseOrderDto> findByCreatedAt(String createdAt);
	public ResponseOrderDto save(ResponseOrderDto orderDto);
}
