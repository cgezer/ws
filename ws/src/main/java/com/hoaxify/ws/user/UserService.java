package com.hoaxify.ws.user;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

	UserRepository userRepository;

	PasswordEncoder passwordEncoder;
	
	public UserService(UserRepository userRepository, PasswordEncoder passworEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passworEncoder;
	}

	public void save(Users users) {
		users.setPassword(this.passwordEncoder.encode(users.getPassword()));
		userRepository.save(users);
		
	}
}
