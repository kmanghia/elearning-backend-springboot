package com.example.demo.repository;

import com.example.demo.model.Course;
import com.example.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
	List<Course> findByInstructor(User instructor);
	List<Course> findByStatus(Course.Status status);
	
	@Query("SELECT c FROM Course c WHERE c.status = :status ORDER BY c.createdAt DESC")
	List<Course> findPublishedCourses(@Param("status") Course.Status status);
	
	@Query("SELECT c FROM Course c JOIN c.enrolledStudents s WHERE s.id = :studentId")
	List<Course> findEnrolledCoursesByStudentId(@Param("studentId") Long studentId);
	
	@Query("SELECT COUNT(c) FROM Course c WHERE c.category.id = :categoryId")
	Long countByCategoryId(@Param("categoryId") Long categoryId);
}

