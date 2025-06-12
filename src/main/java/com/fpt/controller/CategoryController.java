package com.fpt.controller;

import java.util.List;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.modelmapper.ModelMapper;
import com.fpt.form.CategoryFormForCreating;
import com.fpt.form.CategoryFormForUpdating;
import com.fpt.dto.CategoryDTO;
import com.fpt.entity.Category;
import com.fpt.service.ICategoryService;


@RestController
@RequestMapping(value = "api/v1/categorys")
public class CategoryController {

	@Autowired
	private ICategoryService service;
	
	@Autowired
	private ModelMapper modelMapper;

	@GetMapping()
	public Page<CategoryDTO> getAllCategorys(Pageable pageable, @RequestParam(required = false) String search) {
		Page<Category> entityPages = service.getAllCategorys(pageable, search);

		// convert entities --> dtos
			List<CategoryDTO> dtos = modelMapper.map(entityPages.getContent(), new TypeToken<List<CategoryDTO>>() {
			}.getType());

			// add HATEOAS
			for (CategoryDTO dto : dtos) {
				for (CategoryDTO.ProductDTO productDTO : dto.getProducts()) {
					productDTO.add(
							linkTo(methodOn(ProductController.class).getProductByID(productDTO.getId())).withSelfRel());
				}
				dto.add(linkTo(methodOn(CategoryController.class).getCategoryByID(dto.getId())).withSelfRel());
			}

			Page<CategoryDTO> dtoPages = new PageImpl<>(dtos, pageable, entityPages.getTotalElements());

			return dtoPages;
	}

	@GetMapping(value = "/name/{name}")
	public boolean existsCategoryByName(@PathVariable(name = "name") String name) {
		return service.isCategoryExistsByName(name);
	}

	@PostMapping()
	public void createCategory(@RequestBody CategoryFormForCreating form) {
		service.createCategory(form);
	}

	@GetMapping(value = "/{id}")
	public CategoryDTO getCategoryByID(@PathVariable(name = "id") int id) {
		Category entity = service.getCategoryByID(id);

		// convert entity to dto
		CategoryDTO dto = modelMapper.map(entity, CategoryDTO.class);

		dto.add(linkTo(methodOn(CategoryController.class).getCategoryByID(id)).withSelfRel());

		return dto;
	}

	@PutMapping(value = "/{id}")
	public void updateCategory(@PathVariable(name = "id") int id, @RequestBody CategoryFormForUpdating form) {
		form.setId(id);
		service.updateCategory(form);
	}

	@DeleteMapping(value = "/{ids}")
    public void deleteCategorys(@PathVariable(name = "ids") List<Integer> ids) {
        service.deleteCategorys(ids);
    }
}
