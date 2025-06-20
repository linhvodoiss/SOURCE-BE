package com.fpt.dto;

import com.fpt.entity.User;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

@Data
@NoArgsConstructor
public class UserListDTO extends RepresentationModel<UserListDTO> {

	private int id;
	
	private String userName;
	
	private String email;

	private String firstName;

	private String lastName;

	private String phoneNumber;
	private String role;

	public User toEntity() {
		return new User(userName, email, firstName, lastName, phoneNumber,id,role);
	}
}