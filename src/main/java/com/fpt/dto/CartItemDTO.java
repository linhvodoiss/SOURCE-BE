package com.fpt.dto;

import org.springframework.hateoas.RepresentationModel;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CartItemDTO extends RepresentationModel<CartItemDTO> {
	private int id;
	
	private int quantity;
	
	private int product_id;
	
	private String productname;
	
	private float price;
	
	private int cart_id;
	
	private int user_id;
	
	private String username;
	
	
	
}
