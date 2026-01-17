package com.example.demo.service;

import com.example.demo.dto.response.UserResponse;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	public UserResponse getUserById(Long id) {
		User user = userRepository.findById(id)
			.orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
		return mapToResponse(user);
	}

	public UserResponse getUserByEmail(String email) {
		User user = userRepository.findByEmail(email)
			.orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email));
		return mapToResponse(user);
	}

	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	@Transactional
	public UserResponse updateProfile(Long userId, String fullName, String avatarUrl) {
		User user = userRepository.findById(userId)
			.orElseThrow(() -> new ResourceNotFoundException("User not found"));

		if (fullName != null && !fullName.isBlank()) {
			user.setFullName(fullName);
		}
		if (avatarUrl != null) {
			user.setAvatarUrl(avatarUrl);
		}

		user = userRepository.save(user);
		return mapToResponse(user);
	}

	@Transactional
	public void changePassword(Long userId, String currentPassword, String newPassword) {
		User user = userRepository.findById(userId)
			.orElseThrow(() -> new ResourceNotFoundException("User not found"));

		if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
			throw new BadRequestException("Current password is incorrect");
		}

		if (newPassword.length() < 6) {
			throw new BadRequestException("Password must be at least 6 characters long");
		}

		user.setPassword(passwordEncoder.encode(newPassword));
		userRepository.save(user);
	}

	@Transactional
	public void disableUser(Long userId) {
		User user = userRepository.findById(userId)
			.orElseThrow(() -> new ResourceNotFoundException("User not found"));
		user.setEnabled(false);
		userRepository.save(user);
	}

	@Transactional
	public void enableUser(Long userId) {
		User user = userRepository.findById(userId)
			.orElseThrow(() -> new ResourceNotFoundException("User not found"));
		user.setEnabled(true);
		userRepository.save(user);
	}

	private UserResponse mapToResponse(User user) {
		return UserResponse.builder()
			.id(user.getId())
			.email(user.getEmail())
			.fullName(user.getFullName())
			.role(user.getRole())
			.createdAt(user.getCreatedAt())
			.build();
	}

	@Transactional
	public User updateUser(Long id, User userDetails) {
		User user = userRepository.findById(id)
			.orElseThrow(() -> new ResourceNotFoundException("User not found"));
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
		User user = userRepository.findById(id)
			.orElseThrow(() -> new ResourceNotFoundException("User not found"));
		userRepository.delete(user);
	}
}

