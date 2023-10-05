package com.hoaxify.ws.auth;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import com.hoaxify.ws.error.ApiError;
import com.hoaxify.ws.user.User;
import com.hoaxify.ws.user.UserRepository;

@RestController
public class AuthController {
	
	@Autowired
	UserRepository userRepository;
	
	PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	
	@PostMapping("/api/1.0/auth")
	ResponseEntity<?> handleAuthentication(@RequestHeader(name="Authorization" ,required=false) String authorization) {
		if(authorization == null) {
			ApiError error = new ApiError(401, "Anauthorizedrequest", "/api/1.0/auth");
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
		}
		String base64encoded = authorization.split("Basic ")[1];//dXNlcjE6UDRzc3dvcmQ
		String decoded = new String(Base64.getDecoder().decode(base64encoded)); //user1:P4ssword
		String[] parts = decoded.split(":");
		String username = parts[0];
		String password = parts[1];
		User inDB = userRepository.findByUsername(username);
		if(inDB == null) {
			ApiError error = new ApiError(401, "Anauthorizedrequest", "/api/1.0/auth");
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error); 
		}
		String hashedPassword = inDB.getPassword();
		if(!passwordEncoder.matches(password, hashedPassword)) {
			ApiError error = new ApiError(401, "Anauthorizedrequest", "/api/1.0/auth");
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
		}		
		return ResponseEntity.ok(inDB);	
	}
}