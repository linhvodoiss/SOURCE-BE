package com.vti.controller;

import java.util.List;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.modelmapper.ModelMapper;

import com.vti.dto.CartDTO;
import com.vti.dto.ProductDTO;
import com.vti.entity.Cart;
import com.vti.entity.Product;
import com.vti.form.CartFormForCreating;
import com.vti.form.CartFormForUpdating;
import com.vti.service.ICartService;


@RestController
@RequestMapping(value = "api/v1/carts")
public class CartController {

	@Autowired
	private ICartService service;
	
	@Autowired
	private ModelMapper modelMapper;

	@GetMapping()
	public Page<CartDTO> getAllCarts(Pageable pageable,@RequestParam(required = false) String search) {
		Page<Cart> entityPages = service.getAllCarts(pageable, search);

		// convert entities --> dtos
		List<CartDTO> dtos = service.convertToDto(entityPages.getContent());

		return new PageImpl<>(dtos, pageable, entityPages.getTotalElements());

	}
	
	@GetMapping(value = "/list")
    public Page<CartDTO> getListCart(
            @PageableDefault Pageable pageable,
            @RequestParam(required = false) Integer userId) {

        Page<Cart> entityPages = service.getListCart(pageable, userId);

        // convert entities --> dtos
        List<CartDTO> dtos = service.convertToDto(entityPages.getContent());

        Page<CartDTO> dtoPages = new PageImpl<>(dtos, pageable, entityPages.getTotalElements());

        return dtoPages;
    }

	@PostMapping()
	public ResponseEntity<Object> createCart(@RequestBody CartFormForCreating form) {
		Integer cartId = service.createCart(form);
		// Create a response object with only the ID
		Object response = new Object() {
			@JsonProperty("id")
			public Integer id = cartId;
		};
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping(value = "/{id}")
	public CartDTO getCartByID(@PathVariable(name = "id") int id) {
		Cart entity = service.getCartByID(id);

		// convert entity to dto
		CartDTO dto = modelMapper.map(entity, CartDTO.class);
		if (entity.getUser() != null) {
            dto.setUser_id(entity.getUser().getId());
            dto.setUsername(entity.getUser().getUserName());
        }

		dto.add(linkTo(methodOn(CartController.class).getCartByID(id)).withSelfRel());

		return dto;
	}

//	@PutMapping(value = "/{id}")
//	public void updateCart(@PathVariable(name = "id") int id, @RequestBody CartFormForUpdating form) {
//		form.setId(id);
//		service.updateCart(form);
//	}

}
