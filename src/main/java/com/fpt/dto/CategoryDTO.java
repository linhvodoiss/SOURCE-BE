package com.fpt.dto;
import java.util.List;

import org.springframework.hateoas.RepresentationModel;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CategoryDTO extends RepresentationModel<CategoryDTO> {
	public CategoryDTO(int id, String name) {
		this.id = id;
		this.name = name;
	}

	private int id;
	
	private String name;
	
	private String description;
	
	private List<ProductDTO> products;
	
	@Data
	@NoArgsConstructor
	public static class ProductDTO extends RepresentationModel<CategoryDTO>{
		private short id;
		
		private String name;
		
	}

	
}
