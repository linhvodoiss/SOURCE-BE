package com.vti.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.stereotype.Service;

import com.vti.entity.Product;
import com.vti.dto.CartItemDTO;
import com.vti.dto.filter.CartItemFilter;
import com.vti.entity.Cart;
import com.vti.entity.CartItem;
import com.vti.form.CartItemFormForCreating;
import com.vti.form.CartItemFormForUpdating;
import com.vti.repository.CartItemRepository;
import com.vti.repository.CartRepository;
import com.vti.repository.ProductRepository;
import com.vti.specification.CartItemSpecificationBuilder;

@Service
public class CartItemService implements ICartItemService {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private CartItemRepository repository;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private CartRepository cartRepository;
	
	public Page<CartItem> getAllCartItems(Pageable pageable, CartItemFilter filter, String search) {

		CartItemSpecificationBuilder specification = new CartItemSpecificationBuilder(filter, search);

		return repository.findAll(specification.build(), pageable);
	}	
	
	public List<CartItemDTO> convertToDto(List<CartItem> cartitems) {
        List<CartItemDTO> cartitemDTOs = new ArrayList<>();
        for (CartItem cartitem : cartitems) {
            CartItemDTO cartitemDTO = modelMapper.map(cartitem, CartItemDTO.class);
            if (cartitem.getProduct() != null && cartitem.getCart() != null) {
                cartitemDTO.setProduct_id(cartitem.getProduct().getId());
                cartitemDTO.setCart_id(cartitem.getCart().getId());
                cartitemDTO.setPrice(cartitem.getProduct().getPrice());
                cartitemDTO.setProductname(cartitem.getProduct().getName());
                cartitemDTO.setQuantity(cartitem.getQuantity());
                cartitemDTO.setUser_id(cartitem.getCart().getUser().getId());
                cartitemDTO.setUsername(cartitem.getCart().getUser().getUserName());
            }
            cartitemDTOs.add(cartitemDTO);
        }
        return cartitemDTOs;
    }

	public CartItem getCartItemByID(int id) {
		return repository.findById(id).get();
	}
	
	public Page<CartItem> getListCartItem(Pageable pageable, Integer cartId) {
	    if (cartId != null) {
	        return repository.getListCartItemByCartId(pageable, cartId);
	    } else {
	        return repository.findAll(pageable);
	    }
	}

	@Transactional
	public void createCartItem(CartItemFormForCreating form) {
		
		// Convert form to entity
		CartItem cartItemEntity = modelMapper.map(form, CartItem.class);
		
		Integer cart_id = form.getCart_id();	
		Cart cart = cartRepository.findById(cart_id).get();
		cartItemEntity.setCart(cart);
		
		Integer product_id = form.getProduct_id();	
		Product product = productRepository.findById(product_id).get();
		product.setNumber_of_products(product.getNumber_of_products() - cartItemEntity.getQuantity());
		cartItemEntity.setProduct(product);
		
		repository.save(cartItemEntity);

	}

	@Transactional
	public void updateCartItem(CartItemFormForUpdating form) {
		// Fetch existing cart item
		CartItem cartItem = repository.findById(form.getId())
				.orElseThrow(() -> new RuntimeException("CartItem not found"));

		// Update fields (ensure primary key 'id' is not altered)
		cartItem.setQuantity(form.getQuantity());

		// Save updated cart item
		repository.save(cartItem);
	}


	@Override
	@Transactional
	public void deleteCartItems(List<Integer> ids) {
		List<CartItem> cartitems = repository.findAllById(ids);
        List<Product> productEntities = new ArrayList<>();
        List<Cart> cartEntities = new ArrayList<>();
        
        for (CartItem cartitem : cartitems) {
            Product product = cartitem.getProduct();
            Cart cart = cartitem.getCart();
            cartEntities.add(cart);
            productEntities.add(product);
        }
        
        productRepository.saveAll(productEntities);
        cartRepository.saveAll(cartEntities);
        
        repository.deleteByIdIn(ids);
	}

}
