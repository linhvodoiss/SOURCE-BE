package com.fpt.controller;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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

import com.fpt.form.CartItemFormForCreating;
import com.fpt.form.CartItemFormForUpdating;
import com.fpt.dto.CartItemDTO;
import com.fpt.dto.filter.CartItemFilter;
import com.fpt.entity.CartItem;
import com.fpt.service.ICartItemService;


@RestController
@RequestMapping(value = "api/v1/cartitems")
public class CartItemController {

	@Autowired
	private ICartItemService service;
	
	@Autowired
	private ModelMapper modelMapper;

	@GetMapping()
	public Page<CartItemDTO> getAllCartItems(Pageable pageable,CartItemFilter filter, @RequestParam(required = false) String search) {
		Page<CartItem> entityPages = service.getAllCartItems(pageable, filter, search);

		// convert entities --> dtos
		List<CartItemDTO> dtos = service.convertToDto(entityPages.getContent());

		return new PageImpl<>(dtos, pageable, entityPages.getTotalElements());
	}
	
	@GetMapping(value = "/list")
    public Page<CartItemDTO> getListCartItem(
            @PageableDefault Pageable pageable,
            @RequestParam(required = false) Integer cartId) {

        Page<CartItem> entityPages = service.getListCartItem(pageable, cartId);

        // convert entities --> dtos
        List<CartItemDTO> dtos = service.convertToDto(entityPages.getContent());

        Page<CartItemDTO> dtoPages = new PageImpl<>(dtos, pageable, entityPages.getTotalElements());

        return dtoPages;
    }

	@PostMapping()
	public void createCartItem(@RequestBody CartItemFormForCreating form) {
		service.createCartItem(form);
	}

	@GetMapping(value = "/{id}")
	public CartItemDTO getCartItemByID(@PathVariable(name = "id") int id) {
		CartItem entity = service.getCartItemByID(id);

		// convert entity to dto
		CartItemDTO dto = modelMapper.map(entity, CartItemDTO.class);
		if (entity.getCart() != null && entity.getProduct() != null) {
            dto.setProduct_id(entity.getProduct().getId());
            dto.setCart_id(entity.getCart().getId());
            dto.setUser_id(entity.getCart().getUser().getId());
            dto.setPrice(entity.getProduct().getPrice());
            dto.setProductname(entity.getProduct().getName());
            dto.setQuantity(entity.getQuantity());
            
        }

		dto.add(linkTo(methodOn(CartItemController.class).getCartItemByID(id)).withSelfRel());

		return dto;
	}

	@PutMapping(value = "/{id}")
	public void updateCartItem(@PathVariable(name = "id") int id, @RequestBody CartItemFormForUpdating form) {
		form.setId(id);
		service.updateCartItem(form);
	}
	
	@DeleteMapping(value = "/{ids}")
    public void deleteCartItems(@PathVariable(name = "ids") List<Integer> ids) {
        service.deleteCartItems(ids);
    }

}
