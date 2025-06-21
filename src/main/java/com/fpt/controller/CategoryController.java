package com.fpt.controller;

import com.fpt.dto.CategoryDTO;
import com.fpt.service.ICategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {

	private final ICategoryService service;

	@GetMapping
	public List<CategoryDTO> getAll() {
		return service.getAll();
	}

	@GetMapping("/{id}")
	public CategoryDTO getById(@PathVariable Integer id) {
		return service.getById(id);
	}

	@PostMapping
	public CategoryDTO create(@RequestBody CategoryDTO dto) {
		return service.create(dto);
	}

	@PutMapping("/{id}")
	public CategoryDTO update(@PathVariable Integer id, @RequestBody CategoryDTO dto) {
		return service.update(id, dto);
	}

	@DeleteMapping("/{id}")
	public void delete(@PathVariable Integer id) {
		service.delete(id);
	}
}
