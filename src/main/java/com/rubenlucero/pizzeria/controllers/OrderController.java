package com.rubenlucero.pizzeria.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rubenlucero.pizzeria.models.dto.ResponseOrderDto;
import com.rubenlucero.pizzeria.models.services.IOrderService;

@RestController
@RequestMapping("/api")
public class OrderController {
	@Autowired
	private IOrderService orderService;
	
	@GetMapping("/orders")
	public ResponseEntity<?> index(@RequestParam(required = false, name = "date") String date) {
		List<ResponseOrderDto> responseOrders = new ArrayList<>();
		Map<String, Object> response = new HashMap<>();
		
		if (date == null || date.equals(""))
			 responseOrders = orderService.findAll();
		else {
			Pattern pattern = Pattern.compile("([12]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01]))");
			Matcher match = pattern.matcher(date);
			
			if (!match.matches()) {
				response.put("error", "invalid date format");
				return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
			}
			
			responseOrders = orderService.findByCreatedAt(date);			
		}
	
		if (responseOrders.isEmpty()) {
			response.put("error", "orders not found");
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<>(responseOrders, HttpStatus.OK);
	}
		
	@PostMapping("/orders")
	public ResponseEntity<?> create(@Valid @RequestBody ResponseOrderDto orderDto, BindingResult result) {
		Map<String, Object> response = new HashMap<>();
		
		if (result.hasErrors()) {
			List<String> errors = result.getFieldErrors()
					.stream()
					.map(err -> "Campo '" + err.getField() + "' " + err.getDefaultMessage())
					.collect(Collectors.toList());
			
			response.put("errors", errors);
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}
		
		ResponseOrderDto responseOrderDto = orderService.save(orderDto);
		
		if (responseOrderDto == null) {
			response.put("error", "order not created");
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		} else if (responseOrderDto.getTotal() == -1.0) {
			response.put("error", responseOrderDto.getStatus());
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<>(responseOrderDto, HttpStatus.CREATED);
	}

}
