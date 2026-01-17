package com.example.demo.repository;

import com.example.demo.model.PasswordHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PasswordHistoryRepository extends JpaRepository<PasswordHistory, Long> {
	
	@Query("SELECT ph FROM PasswordHistory ph WHERE ph.user.id = :userId ORDER BY ph.changedAt DESC")
	List<PasswordHistory> findByUserIdOrderByChangedAtDesc(@Param("userId") Long userId);
	
	@Query(value = "SELECT ph FROM PasswordHistory ph WHERE ph.user.id = :userId ORDER BY ph.changedAt DESC LIMIT :limit")
	List<PasswordHistory> findTopNByUserId(@Param("userId") Long userId, @Param("limit") int limit);
}
