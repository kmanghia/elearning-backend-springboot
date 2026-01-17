package com.example.demo.repository;

import com.example.demo.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
	List<Notification> findByUserIdOrderByCreatedAtDesc(Long userId);
	List<Notification> findByUserIdAndIsReadFalseOrderByCreatedAtDesc(Long userId);
	Long countByUserIdAndIsReadFalse(Long userId);
}
