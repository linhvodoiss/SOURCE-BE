package com.vti.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.vti.dto.CartDTO;
import com.vti.entity.Cart;
import com.vti.form.CartFormForCreating;
import com.vti.form.CartFormForUpdating;

public interface ICartService {
	Page<Cart> getAllCarts(Pageable pageable, String search);
	
	Integer createCart(CartFormForCreating form);

	public Cart getCartByID(int id);

	public void updateCart(CartFormForUpdating form);

	List<CartDTO> convertToDto(List<Cart> content);

	Page<Cart> getListCart(Pageable pageable, Integer userId);

}
