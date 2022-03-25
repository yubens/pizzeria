package com.rubenlucero.pizzeria.models.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rubenlucero.pizzeria.models.dao.IOrderDao;
import com.rubenlucero.pizzeria.models.dao.IOrderItemDao;
import com.rubenlucero.pizzeria.models.dto.ResponseItemDto;
import com.rubenlucero.pizzeria.models.dto.ResponseOrderDto;
import com.rubenlucero.pizzeria.models.entity.OrderHeader;
import com.rubenlucero.pizzeria.models.entity.OrderItem;
import com.rubenlucero.pizzeria.models.entity.Product;

@Service
public class OrderService implements IOrderService {
	@Autowired
	private IOrderDao orderDao;
	
	@Autowired
	private IOrderItemDao orderItemDao;
	
	@Autowired
	private IProductService productService;

	@Override
	@Transactional(readOnly = true)
	public List<ResponseOrderDto> findAll() {
		List<OrderHeader> orders = (List<OrderHeader>) orderDao.findAll();
		
		if (orders != null) 
			return getOrdersDto(orders);
		
		return new ArrayList<>();
	}
	
	@Override
	@Transactional(readOnly = true)
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

	@Override
	@Transactional
	public ResponseOrderDto save(ResponseOrderDto orderDto) {
		ResponseOrderDto responseOrderDto;
		OrderHeader orderSaved, order;
		Product product;
		OrderItem item;
		List<OrderItem> items = new ArrayList<>();
		int quantity = 0;
		double total = 0.0;
		
		order = new OrderHeader();
		
		for (ResponseItemDto itemDto: orderDto.getItems()) {
			item = new OrderItem();
			product = productService.findById(itemDto.getProductId());
			
			if (product == null) {
				responseOrderDto = new ResponseOrderDto();
				responseOrderDto.setStatus("product not found");
				responseOrderDto.setTotal(-1.0);
				return responseOrderDto;
			}
			
			if (itemDto.getQuantity() <= 0) {
				responseOrderDto = new ResponseOrderDto();
				responseOrderDto.setStatus("invalid quantity");
				responseOrderDto.setTotal(-1.0);
				return responseOrderDto;
			}
			
			item.setQuantity(itemDto.getQuantity());			
			item.setPrice(itemDto.getQuantity() * product.getUnitPrice());
			item.setProduct(product);
			items.add(item);
			
			quantity += item.getQuantity();
			total += item.getPrice();
		}
		
		order.setItems(items);
		order.setAddress(orderDto.getAddress());
		order.setEmail(orderDto.getEmail());
		order.setPhone(orderDto.getPhone());
		order.setSchedule(orderDto.getSchedule());
		order.setStatus("PENDIENTE");
		
		if (quantity > 3) {
			order.setDiscount(true);
			order.setTotal(total * 0.7);
		} else {
			order.setDiscount(false);
			order.setTotal(total);
		}
		
		orderSaved = orderDao.save(order);
		
		if (orderSaved == null) {
			responseOrderDto = new ResponseOrderDto();
			responseOrderDto.setStatus("order not created");
			responseOrderDto.setTotal(-1.0);
			return responseOrderDto;
		}
		
		for (OrderItem itemAux: orderSaved.getItems()) {
			itemAux.setOrderHeader(orderSaved);
			orderItemDao.save(itemAux);
		}
		
		List<OrderHeader> orderHeaders = new ArrayList<>();
		orderHeaders.add(orderSaved);
		
		responseOrderDto = getOrdersDto(orderHeaders).get(0);
		return responseOrderDto;
	}
	
	

}
