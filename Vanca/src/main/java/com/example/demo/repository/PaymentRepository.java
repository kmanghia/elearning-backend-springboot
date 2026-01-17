package com.example.demo.repository;

import com.example.demo.model.Payment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

	Optional<Payment> findByStripePaymentIntentId(String stripePaymentIntentId);

	Page<Payment> findByStudentIdOrderByCreatedAtDesc(Long studentId, Pageable pageable);

	Optional<Payment> findByCourseIdAndStudentId(Long courseId, Long studentId);
}
