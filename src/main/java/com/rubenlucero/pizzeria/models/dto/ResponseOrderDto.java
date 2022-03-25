package com.rubenlucero.pizzeria.models.dto;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class ResponseOrderDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String date;
	private String address;
	private String email;
	private String phone;
	private String schedule;
	private List<ResponseItemDto> items;
	private Double total;
	private boolean discount;
	private String status;	
	
	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	@NotEmpty
	@Size(min = 4, max = 250)
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@NotEmpty
	@Email
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@NotEmpty
	@Pattern(regexp = "^([0-1]?[0-9]|2[0-3]):[0-5][0-9]$", message = "invalid date format")
	public String getSchedule() {
		return schedule;
	}

	public void setSchedule(String schedule) {
		this.schedule = schedule;
	}

	@NotEmpty
	public List<ResponseItemDto> getItems() {
		return items;
	}

	public void setItems(List<ResponseItemDto> items) {
		this.items = items;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	public boolean isDiscount() {
		return discount;
	}

	public void setDiscount(boolean discount) {
		this.discount = discount;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
