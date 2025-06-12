package com.fpt.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.fpt.dto.CartDTO;
import com.fpt.entity.Cart;
import com.fpt.form.CartFormForCreating;
import com.fpt.form.CartFormForUpdating;

public interface ICartService {
	Page<Cart> getAllCarts(Pageable pageable, String search);
	
	Integer createCart(CartFormForCreating form);

	public Cart getCartByID(int id);

	public void updateCart(CartFormForUpdating form);

	List<CartDTO> convertToDto(List<Cart> content);

	Page<Cart> getListCart(Pageable pageable, Integer userId);

}
