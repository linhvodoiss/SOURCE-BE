package com.vti.dto;
import java.util.List;

import org.springframework.hateoas.RepresentationModel;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CartDTO extends RepresentationModel<CartDTO> {
	private int id;
	
	private int user_id;
	
	private String username;
	
	private List<CartItemDTO> cartItems;

	public CartDTO(int id) {
		this.id=id;
	}

	@Data
	@NoArgsConstructor
	public static class CartItemDTO extends RepresentationModel<CartDTO>{
		private short id;
		private int productId;
        private int quantity;
		
	}
}
