package com.fpt.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.fpt.dto.UserListDTO;
import com.fpt.entity.*;
import com.fpt.specification.UserSpecificationBuilder;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fpt.dto.ChangePublicProfileDTO;
import com.fpt.event.OnResetPasswordViaEmailEvent;
import com.fpt.event.OnSendRegistrationUserConfirmViaEmailEvent;
import com.fpt.repository.RegistrationUserTokenRepository;
import com.fpt.repository.ResetPasswordTokenRepository;
import com.fpt.repository.UserRepository;

@Component
@Transactional
public class UserService implements IUserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RegistrationUserTokenRepository registrationUserTokenRepository;

	@Autowired
	private ResetPasswordTokenRepository resetPasswordTokenRepository;

	@Autowired
	private ApplicationEventPublisher eventPublisher;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public Page<User> getAllUser(Pageable pageable, String search,Integer status) {
		UserSpecificationBuilder specification = new UserSpecificationBuilder(search, status);
		return userRepository.findAll(specification.build(), pageable);
	}

	public List<UserListDTO> convertToDto(List<User> users) {
		List<UserListDTO> userDTOs = new ArrayList<>();
		for (User user : users) {
            UserListDTO userDTO = modelMapper.map(user, UserListDTO.class);
			userDTOs.add(userDTO);
		}
		return userDTOs;
	}

	@Override
	public void createUser(User user) {

		// encode password
		user.setPassword(passwordEncoder.encode(user.getPassword()));

		// create user
		userRepository.save(user);

		// create new user registration token
		createNewRegistrationUserToken(user);

		// send email to confirm
		sendConfirmUserRegistrationViaEmail(user.getEmail());
	}

	private void createNewRegistrationUserToken(User user) {

		// create new token for confirm Registration
		final String newToken = UUID.randomUUID().toString();
		RegistrationUserToken token = new RegistrationUserToken(newToken, user);

		registrationUserTokenRepository.save(token);
	}

	@Override
	public void sendConfirmUserRegistrationViaEmail(String email) {
		eventPublisher.publishEvent(new OnSendRegistrationUserConfirmViaEmailEvent(email));
	}

	@Override
	public User findUserByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	@Override
	public User findUserByUserName(String username) {
		return userRepository.findByUserName(username);
	}

	@Override
	public boolean existsUserByEmail(String email) {
		return userRepository.existsByEmail(email);
	}

	@Override
	public boolean existsUserByUserName(String userName) {
		return userRepository.existsByUserName(userName);
	}

	@Override
	public boolean existsUserByPhoneNumber(String phoneNumber) {
		return userRepository.existsByPhoneNumber(phoneNumber);
	}

	@Override
	public void activeUser(String token) {

		// get token
		RegistrationUserToken registrationUserToken = registrationUserTokenRepository.findByToken(token);

		// active user
		User user = registrationUserToken.getUser();
		user.setStatus(UserStatus.ACTIVE);
		userRepository.save(user);

		// remove Registration User Token
		registrationUserTokenRepository.deleteById(registrationUserToken.getId());
	}

	@Override
	public void resetPasswordViaEmail(String email) {

		// find user by email
		User user = findUserByEmail(email);

		// remove token token if exists
		resetPasswordTokenRepository.deleteByUserId(user.getId());

		// create new reset password token
		createNewResetPasswordToken(user);

		// send email
		sendResetPasswordViaEmail(email);
	}

	@Override
	public void sendResetPasswordViaEmail(String email) {
		eventPublisher.publishEvent(new OnResetPasswordViaEmailEvent(email));
	}

	private void createNewResetPasswordToken(User user) {

		// create new token for Reseting password
		final String newToken = UUID.randomUUID().toString();
		ResetPasswordToken token = new ResetPasswordToken(newToken, user);

		resetPasswordTokenRepository.save(token);
	}

	@Override
	public void resetPassword(String token, String newPassword) {
		// get token
		ResetPasswordToken resetPasswordToken = resetPasswordTokenRepository.findByToken(token);

		// change password
		User user = resetPasswordToken.getUser();
		user.setPassword(passwordEncoder.encode(newPassword));
		userRepository.save(user);

		// remove Reset Password
		resetPasswordTokenRepository.deleteById(resetPasswordToken.getId());
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// Check user exists by username
		User user = userRepository.findByUserName(username);

		if (user == null) {
			throw new UsernameNotFoundException(username);
		}

		return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(),
				AuthorityUtils.createAuthorityList(String.valueOf(user.getRole())));
	}

	@Override
	public void changeUserProfile(String username, ChangePublicProfileDTO dto) {
		User user = userRepository.findByUserName(username);
		
		user.setAvatarUrl(dto.getAvatarUrl());
		user.setFirstName(dto.getFirstName());
		user.setLastName(dto.getLastName());
		user.setEmail(dto.getEmail());
		user.setFullName(dto.getFirstName() + dto.getLastName());
		user.setPhoneNumber(dto.getPhoneNumber());
		userRepository.save(user);
		
		// TODO other field
	}

}
