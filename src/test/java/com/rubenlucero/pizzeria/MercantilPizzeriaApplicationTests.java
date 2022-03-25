package com.rubenlucero.pizzeria;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.rubenlucero.pizzeria.models.dto.ResponseItemDto;
import com.rubenlucero.pizzeria.models.dto.ResponseOrderDto;
import com.rubenlucero.pizzeria.models.services.IOrderService;

@SpringBootTest
class MercantilPizzeriaApplicationTests {;	
	
	@Autowired
	private IOrderService orderService;	

//	@Test
//	void contextLoads() {
//	}
		
	@Test
    public void createOrder_Error() {
		ResponseOrderDto orderDto = new ResponseOrderDto();		
		
		orderDto.setEmail("ruben@mail.com");
		orderDto.setAddress("españa 150");
		orderDto.setSchedule("21:00");
		
		List<ResponseItemDto> items = new ArrayList<ResponseItemDto>();
		ResponseItemDto item = new ResponseItemDto();
		
		item.setProductId(2L);
		item.setQuantity(-1);		
		items.add(item);		
		orderDto.setItems(items);
        
		ResponseOrderDto responseSaved =  orderService.save(orderDto);
		
        assertEquals(responseSaved.getStatus(), "invalid quantity");
    }
	
	@Test
    public void createOrder_Ok() {
		ResponseOrderDto orderDto = new ResponseOrderDto();		
		
		orderDto.setEmail("ruben@mail.com");
		orderDto.setAddress("garibaldi 7, mendoza");
		orderDto.setSchedule("14:30");
		
		List<ResponseItemDto> items = new ArrayList<ResponseItemDto>();
		ResponseItemDto item = new ResponseItemDto();
		
		item.setProductId(1L);
		item.setQuantity(5);		
		items.add(item);		
		orderDto.setItems(items);
        
		ResponseOrderDto responseSaved =  orderService.save(orderDto);
		
        assertEquals(responseSaved.getStatus(), "PENDIENTE");
    }
	
	@Test
    public void findAllOrders_Ok() {
        List<ResponseOrderDto> orders = orderService.findAll();     
        
        assertEquals(orders.size(), 4);
    }

}
