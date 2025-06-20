package com.fpt.controller;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import com.fpt.dto.*;
import com.fpt.dto.filter.ProductFilter;
import com.fpt.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fpt.entity.User;
import com.fpt.service.IUserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin("*")
@RestController
@RequestMapping(value = "/api/v1/users")
@Validated
public class UserController {

	@Autowired
	private IUserService userService;
	@GetMapping("/checkEmail")
	public ResponseEntity<?> existsUserByEmail(@RequestParam(name = "email") String email) {
		// get entity
		boolean result = userService.existsUserByEmail(email);
		Map<String, Object> response = new HashMap<>();
		response.put("code", HttpServletResponse.SC_OK);
		response.put("check", result);
		// return result
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("/checkUserName")
	public ResponseEntity<?> existsUserByUserName(@RequestParam(name = "userName") String userName) {
		// get entity
		boolean result = userService.existsUserByUserName(userName);
		Map<String, Object> response = new HashMap<>();
		response.put("code", HttpServletResponse.SC_OK);
		response.put("check", result);
		// return result
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("/checkPhoneNumber")
	public ResponseEntity<?> existsUserByPhoneNumber(@RequestParam(name = "phoneNumber") String phoneNumber) {
		// get entity
		boolean result = userService.existsUserByPhoneNumber(phoneNumber);
		Map<String, Object> response = new HashMap<>();
		response.put("code", HttpServletResponse.SC_OK);
		response.put("check", result);
		// return result
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PostMapping()
	public ResponseEntity<Map<String, Object>> createUser(@Valid @RequestBody UserDTO dto) {
		try {
			userService.createUser(dto.toEntity());

			Map<String, Object> response = new HashMap<>();
			response.put("code", HttpServletResponse.SC_OK);
			response.put("message", "Đăng ký tài khoản thành công, vui lòng check email để kích hoạt tài khoản!");

			return ResponseEntity.ok(response);
		} catch (Exception e) {
			Map<String, Object> errorResponse = new HashMap<>();
			errorResponse.put("code", HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			errorResponse.put("message", "Đăng ký tài khoản thất bại");

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
		}
	}


	@GetMapping("/activeUser")
	// validate: check exists, check not expired
	public ResponseEntity<?> activeUserViaEmail(@RequestParam String token) {
		// active user
		userService.activeUser(token);

		return new ResponseEntity<>("Active success!", HttpStatus.OK);
	}

	// resend confirm
	@GetMapping("/userRegistrationConfirmRequest")
	// validate: email exists, email not active
	public ResponseEntity<?> resendConfirmRegistrationViaEmail(@RequestParam String email) {

		userService.sendConfirmUserRegistrationViaEmail(email);

		return new ResponseEntity<>("We have sent an email. Please check email to active account!", HttpStatus.OK);
	}

	// reset password confirm
	@GetMapping("/resetPasswordRequest")
	// validate: email exists, email not active
	public ResponseEntity<?> sendResetPasswordViaEmail(@RequestParam String email) {

		userService.resetPasswordViaEmail(email);
		Map<String, Object> response = new HashMap<>();
		response.put("code", HttpServletResponse.SC_OK);
		response.put("message", "Chúng tôi đã gửi email, Vui lòng kiểm tra email "+email+" để đổi mật khẩu");
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	// resend reset password
	@GetMapping("/resendResetPassword")
	// validate: email exists, email not active
	public ResponseEntity<?> resendResetPasswordViaEmail(@RequestParam String email) {

		userService.sendResetPasswordViaEmail(email);

		return new ResponseEntity<>("We have sent an email. Please check email to reset password!", HttpStatus.OK);
	}

	@GetMapping("/resetPassword")
	// validate: check exists, check not expired
	public ResponseEntity<?> resetPasswordViaEmail(@RequestParam String token, @RequestParam String newPassword) {
		Map<String, Object> response = new HashMap<>();
		try {
			// reset password
			userService.resetPassword(token, newPassword);

			response.put("code", HttpServletResponse.SC_OK);
			response.put("message", "Cập nhật mật khẩu thành công");
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			response.put("code", HttpServletResponse.SC_NOT_FOUND);
			response.put("message", "Cập nhật mật khẩu thất bại");
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/profile")
	// validate: check exists, check not expired
	public ResponseEntity<?> getUserProfile(Authentication authentication) {
		
		// get username from token
		String username = authentication.getName();
		
		// get user info
		User user = userService.findUserByUserName(username);
		
        // convert user entity to user dto
		ProfileDTO profileDto = new ProfileDTO(
        		user.getUserName(), 
        		user.getEmail(), 
        		user.getFirstName(), 
        		user.getLastName(),
        		user.getPhoneNumber(),
        		user.getRole(),
        		user.getStatus().toString(),
        		user.getAvatarUrl());

		return new ResponseEntity<>(profileDto, HttpStatus.OK);
	}
	
	@PutMapping("/profile")
	// validate: check exists, check not expired
	public ResponseEntity<?> changeUserProfile(Authentication authentication, @RequestBody ChangePublicProfileDTO dto) {
		
		// get username from token
		String username = authentication.getName();
		
		userService.changeUserProfile(username, dto);
		
		return new ResponseEntity<>("Change Profile Successfully!", HttpStatus.OK);
	}

}
