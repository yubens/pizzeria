package com.rubenlucero.pizzeria.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.rubenlucero.pizzeria.models.dto.ResponseOrderDto;
import com.rubenlucero.pizzeria.models.services.IOrderService;

@RestController
@RequestMapping("/api")
public class OrderController {
	@Autowired
	private IOrderService orderService;
	
	@GetMapping("/orders")
	public List<ResponseOrderDto> index() {
		return orderService.findAll();
	}
	
	@GetMapping("/orders/{date}")
	public List<ResponseOrderDto> ordersByDate(@PathVariable String date) {
		return orderService.findByCreatedAt(date);
	}
	
	@PostMapping("/orders")
	@ResponseStatus(HttpStatus.CREATED)	
	public ResponseOrderDto create(@RequestBody ResponseOrderDto orderDto) {
		return orderService.save(orderDto);
	}

}
