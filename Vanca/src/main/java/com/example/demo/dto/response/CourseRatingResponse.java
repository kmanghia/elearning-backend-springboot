package com.example.demo.dto.response;

import java.util.Map;

public class CourseRatingResponse {

	private Long courseId;
	private Double averageRating;
	private Long totalReviews;
	private Map<Integer, Long> ratingDistribution; // Key: star rating (1-5), Value: count

	public CourseRatingResponse() {
	}

	public static Builder builder() {
		return new Builder();
	}

	public static class Builder {
		private Long courseId;
		private Double averageRating;
		private Long totalReviews;
		private Map<Integer, Long> ratingDistribution;

		public Builder courseId(Long courseId) {
			this.courseId = courseId;
			return this;
		}

		public Builder averageRating(Double averageRating) {
			this.averageRating = averageRating;
			return this;
		}

		public Builder totalReviews(Long totalReviews) {
			this.totalReviews = totalReviews;
			return this;
		}

		public Builder ratingDistribution(Map<Integer, Long> ratingDistribution) {
			this.ratingDistribution = ratingDistribution;
			return this;
		}

		public CourseRatingResponse build() {
			CourseRatingResponse response = new CourseRatingResponse();
			response.setCourseId(courseId);
			response.setAverageRating(averageRating);
			response.setTotalReviews(totalReviews);
			response.setRatingDistribution(ratingDistribution);
			return response;
		}
	}

	public Long getCourseId() {
		return courseId;
	}

	public void setCourseId(Long courseId) {
		this.courseId = courseId;
	}

	public Double getAverageRating() {
		return averageRating;
	}

	public void setAverageRating(Double averageRating) {
		this.averageRating = averageRating;
	}

	public Long getTotalReviews() {
		return totalReviews;
	}

	public void setTotalReviews(Long totalReviews) {
		this.totalReviews = totalReviews;
	}

	public Map<Integer, Long> getRatingDistribution() {
		return ratingDistribution;
	}

	public void setRatingDistribution(Map<Integer, Long> ratingDistribution) {
		this.ratingDistribution = ratingDistribution;
	}
}
