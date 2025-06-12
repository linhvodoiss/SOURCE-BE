package com.vti.dto.filter;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CartItemFilter {
	private int minQuantity;

	private int maxQuantity;
	
}
