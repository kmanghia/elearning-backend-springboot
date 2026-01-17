package com.example.demo.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CourseRatingResponse {
	
	private Long courseId;
	private Double averageRating;
	private Long totalReviews;
	private Map<Integer, Long> ratingDistribution; // Key: star rating (1-5), Value: count
}
