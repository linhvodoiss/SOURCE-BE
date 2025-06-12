package com.vti.form;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductFormForUpdating {
	private int id;

	private String name;
	
	private int category_id;
	
	private int number_of_products;
	
	private float price;
	
	private String thumbnailUrl;
	
	private String description;
	
}
