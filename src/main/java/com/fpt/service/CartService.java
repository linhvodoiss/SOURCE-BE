package com.fpt.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.stereotype.Service;

import com.fpt.entity.User;
import com.fpt.dto.CartDTO;
import com.fpt.entity.Cart;
import com.fpt.form.CartFormForCreating;
import com.fpt.form.CartFormForUpdating;
import com.fpt.repository.CartItemRepository;
import com.fpt.repository.CartRepository;
import com.fpt.repository.ProductRepository;
import com.fpt.repository.UserRepository;
import com.fpt.specification.CartSpecificationBuilder;

@Service
public class CartService implements ICartService {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private CartRepository repository;
	
	@Autowired
	private CartItemRepository cartItemRepository;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	public Page<Cart> getAllCarts(Pageable pageable, String search) {

		CartSpecificationBuilder specification = new CartSpecificationBuilder(search);

		return repository.findAll(specification.build(), pageable);
	}	
	
	public List<CartDTO> convertToDto(List<Cart> carts) {
        List<CartDTO> cartDTOs = new ArrayList<>();
        for (Cart cart : carts) {
        	CartDTO cartDTO = modelMapper.map(cart, CartDTO.class);
            if (cart.getUser() != null) {
                cartDTO.setUser_id(cart.getUser().getId());;
                cartDTO.setUsername(cart.getUser().getUserName());
            }
            cartDTOs.add(cartDTO);
        }
        return cartDTOs;
    }

	public Cart getCartByID(int id) {
		return repository.findById(id).get();
	}

	@Transactional
	public Integer createCart(CartFormForCreating form) {
		// omit id field
		TypeMap<CartFormForCreating, Cart> typeMap = modelMapper.getTypeMap(CartFormForCreating.class, Cart.class);
		if (typeMap == null) { // if not already added
			// skip field
			modelMapper.addMappings(new PropertyMap<CartFormForCreating, Cart>() {
				@Override
				protected void configure() {
					skip(destination.getId());
				}
			});
		}
		// convert form to entity
		Cart cartEntity = modelMapper.map(form, Cart.class);

		Integer user_id = form.getUser_id();
		User user = userRepository.findById(user_id).get();
		cartEntity.setUser(user);
		// Save cart
		Cart savedCart = repository.save(cartEntity);
		return savedCart.getId();
	}

	@Transactional
	public void updateCart(CartFormForUpdating form) {

//		// fetch existing product
//		Cart cart = repository.findById(form.getId())
//				.orElseThrow(() -> new RuntimeException("Cart not found"));
//
//		// update fields
//		modelMapper.map(form, cart);
//
//		// save updated product
//		repository.save(cart);
	}

	@Override
	public Page<Cart> getListCart(Pageable pageable, Integer userId) {
		if (userId != null) {
	        return repository.getListCartByUserId(pageable, userId);
	    } else {
	        return repository.findAll(pageable);
	    }
	}

}
