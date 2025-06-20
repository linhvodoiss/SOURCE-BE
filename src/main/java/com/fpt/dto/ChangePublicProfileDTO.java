package com.fpt.dto;

import lombok.Data;

@Data
public class ChangePublicProfileDTO {

	// TODO validate
	private String avatarUrl;

	private String biography;
	
	private String userName;
	
	private String email;

	private String firstName;

	private String lastName;
	
	private String phoneNumber;

	public ChangePublicProfileDTO() {
	}

}
