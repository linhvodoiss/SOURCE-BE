package com.fpt.dto;

import com.fpt.entity.Role;
import org.springframework.hateoas.RepresentationModel;

import com.fpt.entity.User;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class UserDTO extends RepresentationModel<UserDTO> {

	private int id;
	
	private String userName;
	
	private String email;

	private String password;

	private String firstName;

	private String lastName;

	private String phoneNumber;
	private Role role=Role.CUSTOMER;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;

	public User toEntity() {
		return new User(userName, email, password, firstName, lastName, phoneNumber,id,role);
	}

}