package com.example.demo.service;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;

	public User getUserById(Long id) {
		return userRepository.findById(id)
			.orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
	}

	public User getUserByEmail(String email) {
		return userRepository.findByEmail(email)
			.orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email));
	}

	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	@Transactional
	public User updateUser(Long id, User userDetails) {
		User user = getUserById(id);
		if (userDetails.getFullName() != null) {
			user.setFullName(userDetails.getFullName());
		}
		if (userDetails.getAvatarUrl() != null) {
			user.setAvatarUrl(userDetails.getAvatarUrl());
		}
		return userRepository.save(user);
	}

	@Transactional
	public void deleteUser(Long id) {
		User user = getUserById(id);
		userRepository.delete(user);
	}
}

