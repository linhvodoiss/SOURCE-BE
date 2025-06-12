package com.fpt.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.fpt.dto.CartItemDTO;
import com.fpt.dto.filter.CartItemFilter;
import com.fpt.entity.CartItem;
import com.fpt.form.CartItemFormForCreating;
import com.fpt.form.CartItemFormForUpdating;

public interface ICartItemService {
	Page<CartItem> getAllCartItems(Pageable pageable, CartItemFilter filter, String search);

	void createCartItem(CartItemFormForCreating form);

	public CartItem getCartItemByID(int id);

	public void updateCartItem(CartItemFormForUpdating form);

	List<CartItemDTO> convertToDto(List<CartItem> content);

	public void deleteCartItems(List<Integer> ids);

	public Page<CartItem> getListCartItem(Pageable pageable, Integer cartId);

}
