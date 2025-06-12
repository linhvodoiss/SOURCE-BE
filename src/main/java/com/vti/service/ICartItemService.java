package com.vti.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.vti.dto.CartItemDTO;
import com.vti.dto.filter.CartItemFilter;
import com.vti.entity.Cart;
import com.vti.entity.CartItem;
import com.vti.form.CartItemFormForCreating;
import com.vti.form.CartItemFormForUpdating;

public interface ICartItemService {
	Page<CartItem> getAllCartItems(Pageable pageable, CartItemFilter filter, String search);

	void createCartItem(CartItemFormForCreating form);

	public CartItem getCartItemByID(int id);

	public void updateCartItem(CartItemFormForUpdating form);

	List<CartItemDTO> convertToDto(List<CartItem> content);

	public void deleteCartItems(List<Integer> ids);

	public Page<CartItem> getListCartItem(Pageable pageable, Integer cartId);

}
