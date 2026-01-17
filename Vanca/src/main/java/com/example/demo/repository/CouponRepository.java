package com.example.demo.repository;

import com.example.demo.model.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CouponRepository extends JpaRepository<Coupon, Long> {
	
	Optional<Coupon> findByCode(String code);
	
	Optional<Coupon> findByCodeAndActiveTrue(String code);
	
	boolean existsByCode(String code);
}
