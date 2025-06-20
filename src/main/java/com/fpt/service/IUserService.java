package com.fpt.service;

import com.fpt.dto.ProductDTO;
import com.fpt.dto.UserDTO;
import com.fpt.dto.UserListDTO;
import com.fpt.dto.filter.ProductFilter;
import com.fpt.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.fpt.dto.ChangePublicProfileDTO;
import com.fpt.entity.User;

import java.util.List;

public interface IUserService extends UserDetailsService {

	Page<User> getAllUser(Pageable pageable, String search);

	void createUser(User user);

	User findUserByEmail(String email);

	User findUserByUserName(String username);

	void activeUser(String token);

	void sendConfirmUserRegistrationViaEmail(String email);

	boolean existsUserByEmail(String email);

	boolean existsUserByUserName(String userName);

	boolean existsUserByPhoneNumber(String phoneNumber);
	void resetPasswordViaEmail(String email);

	void resetPassword(String token, String newPassword);

	void sendResetPasswordViaEmail(String email);

	UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
	
	void changeUserProfile(String username, ChangePublicProfileDTO dto);
	List<UserListDTO> convertToDto(List<User> data);
}
