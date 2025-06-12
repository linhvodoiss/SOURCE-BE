package com.vti.form;

import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
public class ProductFormForCreating {
	
	private int category_id;
	
	private String name;
	
	private int number_of_products;
	
	private float price;
	
	private String thumbnailUrl;
	
	private String description;
}
