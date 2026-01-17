package com.example.demo.dto.response;

import java.util.List;
import java.util.Map;

public class StudentAnalyticsResponse {
	private Long studentId;
	private String studentName;

	// Overall stats
	private Integer totalEnrolledCourses;
	private Integer completedCourses;
	private Double overallCompletionRate;

	// Quiz performance
	private Integer totalQuizzes;
	private Double averageQuizScore;
	private Integer passedQuizzes;
	private Integer failedQuizzes;

	// Learning activity
	private Integer totalLessonsCompleted;
	private Map<String, Integer> learningStreak; // Days active in last week/month

	// Course progress breakdown
	private List<CourseProgressSummary> courseProgressList;

	public StudentAnalyticsResponse() {
	}

	public static Builder builder() {
		return new Builder();
	}

	public static class Builder {
		private Long studentId;
		private String studentName;
		private Integer totalEnrolledCourses;
		private Integer completedCourses;
		private Double overallCompletionRate;
		private Integer totalQuizzes;
		private Double averageQuizScore;
		private Integer passedQuizzes;
		private Integer failedQuizzes;
		private Integer totalLessonsCompleted;
		private Map<String, Integer> learningStreak;
		private List<CourseProgressSummary> courseProgressList;

		public Builder studentId(Long studentId) {
			this.studentId = studentId;
			return this;
		}

		public Builder studentName(String studentName) {
			this.studentName = studentName;
			return this;
		}

		public Builder totalEnrolledCourses(Integer totalEnrolledCourses) {
			this.totalEnrolledCourses = totalEnrolledCourses;
			return this;
		}

		public Builder completedCourses(Integer completedCourses) {
			this.completedCourses = completedCourses;
			return this;
		}

		public Builder overallCompletionRate(Double overallCompletionRate) {
			this.overallCompletionRate = overallCompletionRate;
			return this;
		}

		public Builder totalQuizzes(Integer totalQuizzes) {
			this.totalQuizzes = totalQuizzes;
			return this;
		}

		public Builder averageQuizScore(Double averageQuizScore) {
			this.averageQuizScore = averageQuizScore;
			return this;
		}

		public Builder passedQuizzes(Integer passedQuizzes) {
			this.passedQuizzes = passedQuizzes;
			return this;
		}

		public Builder failedQuizzes(Integer failedQuizzes) {
			this.failedQuizzes = failedQuizzes;
			return this;
		}

		public Builder totalLessonsCompleted(Integer totalLessonsCompleted) {
			this.totalLessonsCompleted = totalLessonsCompleted;
			return this;
		}

		public Builder learningStreak(Map<String, Integer> learningStreak) {
			this.learningStreak = learningStreak;
			return this;
		}

		public Builder courseProgressList(List<CourseProgressSummary> courseProgressList) {
			this.courseProgressList = courseProgressList;
			return this;
		}

		public StudentAnalyticsResponse build() {
			StudentAnalyticsResponse response = new StudentAnalyticsResponse();
			response.setStudentId(studentId);
			response.setStudentName(studentName);
			response.setTotalEnrolledCourses(totalEnrolledCourses);
			response.setCompletedCourses(completedCourses);
			response.setOverallCompletionRate(overallCompletionRate);
			response.setTotalQuizzes(totalQuizzes);
			response.setAverageQuizScore(averageQuizScore);
			response.setPassedQuizzes(passedQuizzes);
			response.setFailedQuizzes(failedQuizzes);
			response.setTotalLessonsCompleted(totalLessonsCompleted);
			response.setLearningStreak(learningStreak);
			response.setCourseProgressList(courseProgressList);
			return response;
		}
	}

	// Getters and setters
	public Long getStudentId() {
		return studentId;
	}

	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public Integer getTotalEnrolledCourses() {
		return totalEnrolledCourses;
	}

	public void setTotalEnrolledCourses(Integer totalEnrolledCourses) {
		this.totalEnrolledCourses = totalEnrolledCourses;
	}

	public Integer getCompletedCourses() {
		return completedCourses;
	}

	public void setCompletedCourses(Integer completedCourses) {
		this.completedCourses = completedCourses;
	}

	public Double getOverallCompletionRate() {
		return overallCompletionRate;
	}

	public void setOverallCompletionRate(Double overallCompletionRate) {
		this.overallCompletionRate = overallCompletionRate;
	}

	public Integer getTotalQuizzes() {
		return totalQuizzes;
	}

	public void setTotalQuizzes(Integer totalQuizzes) {
		this.totalQuizzes = totalQuizzes;
	}

	public Double getAverageQuizScore() {
		return averageQuizScore;
	}

	public void setAverageQuizScore(Double averageQuizScore) {
		this.averageQuizScore = averageQuizScore;
	}

	public Integer getPassedQuizzes() {
		return passedQuizzes;
	}

	public void setPassedQuizzes(Integer passedQuizzes) {
		this.passedQuizzes = passedQuizzes;
	}

	public Integer getFailedQuizzes() {
		return failedQuizzes;
	}

	public void setFailedQuizzes(Integer failedQuizzes) {
		this.failedQuizzes = failedQuizzes;
	}

	public Integer getTotalLessonsCompleted() {
		return totalLessonsCompleted;
	}

	public void setTotalLessonsCompleted(Integer totalLessonsCompleted) {
		this.totalLessonsCompleted = totalLessonsCompleted;
	}

	public Map<String, Integer> getLearningStreak() {
		return learningStreak;
	}

	public void setLearningStreak(Map<String, Integer> learningStreak) {
		this.learningStreak = learningStreak;
	}

	public List<CourseProgressSummary> getCourseProgressList() {
		return courseProgressList;
	}

	public void setCourseProgressList(List<CourseProgressSummary> courseProgressList) {
		this.courseProgressList = courseProgressList;
	}

	// Nested class
	public static class CourseProgressSummary {
		private Long courseId;
		private String courseTitle;
		private Double completionPercentage;
		private Integer completedLessons;
		private Integer totalLessons;
		private String status; // NOT_STARTED, IN_PROGRESS, COMPLETED

		public CourseProgressSummary() {
		}

		public static Builder builder() {
			return new Builder();
		}

		public static class Builder {
			private Long courseId;
			private String courseTitle;
			private Double completionPercentage;
			private Integer completedLessons;
			private Integer totalLessons;
			private String status;

			public Builder courseId(Long courseId) {
				this.courseId = courseId;
				return this;
			}

			public Builder courseTitle(String courseTitle) {
				this.courseTitle = courseTitle;
				return this;
			}

			public Builder completionPercentage(Double completionPercentage) {
				this.completionPercentage = completionPercentage;
				return this;
			}

			public Builder completedLessons(Integer completedLessons) {
				this.completedLessons = completedLessons;
				return this;
			}

			public Builder totalLessons(Integer totalLessons) {
				this.totalLessons = totalLessons;
				return this;
			}

			public Builder status(String status) {
				this.status = status;
				return this;
			}

			public CourseProgressSummary build() {
				CourseProgressSummary summary = new CourseProgressSummary();
				summary.setCourseId(courseId);
				summary.setCourseTitle(courseTitle);
				summary.setCompletionPercentage(completionPercentage);
				summary.setCompletedLessons(completedLessons);
				summary.setTotalLessons(totalLessons);
				summary.setStatus(status);
				return summary;
			}
		}

		public Long getCourseId() {
			return courseId;
		}

		public void setCourseId(Long courseId) {
			this.courseId = courseId;
		}

		public String getCourseTitle() {
			return courseTitle;
		}

		public void setCourseTitle(String courseTitle) {
			this.courseTitle = courseTitle;
		}

		public Double getCompletionPercentage() {
			return completionPercentage;
		}

		public void setCompletionPercentage(Double completionPercentage) {
			this.completionPercentage = completionPercentage;
		}

		public Integer getCompletedLessons() {
			return completedLessons;
		}

		public void setCompletedLessons(Integer completedLessons) {
			this.completedLessons = completedLessons;
		}

		public Integer getTotalLessons() {
			return totalLessons;
		}

		public void setTotalLessons(Integer totalLessons) {
			this.totalLessons = totalLessons;
		}

		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}
	}
}
