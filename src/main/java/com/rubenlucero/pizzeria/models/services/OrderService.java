package com.rubenlucero.pizzeria.models.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rubenlucero.pizzeria.models.dao.IOrderDao;
import com.rubenlucero.pizzeria.models.dto.ResponseItemDto;
import com.rubenlucero.pizzeria.models.dto.ResponseOrderDto;
import com.rubenlucero.pizzeria.models.entity.OrderHeader;
import com.rubenlucero.pizzeria.models.entity.OrderItem;

@Service
public class OrderService implements IOrderService {
	@Autowired
	private IOrderDao orderDao;

	@Override
	public List<ResponseOrderDto> findAll() {
		List<OrderHeader> orders = (List<OrderHeader>) orderDao.findAll();
		
		if (orders != null) 
			return getOrdersDto(orders);
		
		return new ArrayList<>();
	}
	
	@Override
	public List<ResponseOrderDto> findByCreatedAt(String createdAt) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date aux = null;
		List<OrderHeader> orders = new ArrayList<>();

		try {
			aux = formatter.parse(createdAt);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		 orders = (List<OrderHeader>) orderDao.findByCreatedAt(aux);
		 
		 if (orders != null) 
				return getOrdersDto(orders);
			
			return new ArrayList<>();
	}
	
	private List<ResponseOrderDto> getOrdersDto(List<OrderHeader> orders) {
		List<ResponseOrderDto> response = new ArrayList<>();		
		ResponseOrderDto orderDto;
		List<ResponseItemDto> items;
		ResponseItemDto itemDto;
		
		for (OrderHeader order: orders) {
			orderDto = new ResponseOrderDto();			
			items = new ArrayList<>();
			
			for (OrderItem item: order.getItems()) {
				itemDto = new ResponseItemDto();
				itemDto.setPrice(item.getPrice());
				itemDto.setQuantity(item.getQuantity());
				itemDto.setProductId(item.getProduct().getId());
				itemDto.setProductName(item.getProduct().getName());
				items.add(itemDto);
			}
			
			orderDto.setItems(items);
			orderDto.setAddress(order.getAddress());
			orderDto.setDiscount(order.isDiscount());
			orderDto.setEmail(order.getEmail());
			orderDto.setPhone(order.getPhone());
			orderDto.setSchedule(order.getSchedule());
			orderDto.setStatus(order.getStatus());
			orderDto.setTotal(order.getTotal());
			
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

			String aux = null;

			try {
				aux = formatter.format(order.getCreatedAt());
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			orderDto.setDate(aux);
			
			response.add(orderDto);
			
			
		}
		
		return response;
	}

}
