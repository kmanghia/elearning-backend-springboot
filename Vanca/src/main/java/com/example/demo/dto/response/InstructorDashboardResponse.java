package com.example.demo.dto.response;

import java.util.List;

public class InstructorDashboardResponse {
    private Long instructorId;
    private String instructorName;

    // Overall statistics
    private Integer totalCourses;
    private Integer publishedCourses;
    private Integer totalStudents; // Unique students across all courses
    private Integer totalEnrollments;
    private Double overallAverageRating;
    private Integer totalReviews;

    // Engagement summary
    private Integer activeStudents; // Active in last 7 days
    private Double averageCompletionRate;
    private Integer totalQuizAttempts;

    // Course-specific metrics
    private List<CoursePerformanceDTO> coursePerformances;

    // Recent activity
    private Integer newEnrollmentsThisWeek;
    private Integer newReviewsThisWeek;

    public InstructorDashboardResponse() {
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long instructorId;
        private String instructorName;
        private Integer totalCourses;
        private Integer publishedCourses;
        private Integer totalStudents;
        private Integer totalEnrollments;
        private Double overallAverageRating;
        private Integer totalReviews;
        private Integer activeStudents;
        private Double averageCompletionRate;
        private Integer totalQuizAttempts;
        private List<CoursePerformanceDTO> coursePerformances;
        private Integer newEnrollmentsThisWeek;
        private Integer newReviewsThisWeek;

        public Builder instructorId(Long instructorId) {
            this.instructorId = instructorId;
            return this;
        }

        public Builder instructorName(String instructorName) {
            this.instructorName = instructorName;
            return this;
        }

        public Builder totalCourses(Integer totalCourses) {
            this.totalCourses = totalCourses;
            return this;
        }

        public Builder publishedCourses(Integer publishedCourses) {
            this.publishedCourses = publishedCourses;
            return this;
        }

        public Builder totalStudents(Integer totalStudents) {
            this.totalStudents = totalStudents;
            return this;
        }

        public Builder totalEnrollments(Integer totalEnrollments) {
            this.totalEnrollments = totalEnrollments;
            return this;
        }

        public Builder overallAverageRating(Double overallAverageRating) {
            this.overallAverageRating = overallAverageRating;
            return this;
        }

        public Builder totalReviews(Integer totalReviews) {
            this.totalReviews = totalReviews;
            return this;
        }

        public Builder activeStudents(Integer activeStudents) {
            this.activeStudents = activeStudents;
            return this;
        }

        public Builder averageCompletionRate(Double averageCompletionRate) {
            this.averageCompletionRate = averageCompletionRate;
            return this;
        }

        public Builder totalQuizAttempts(Integer totalQuizAttempts) {
            this.totalQuizAttempts = totalQuizAttempts;
            return this;
        }

        public Builder coursePerformances(List<CoursePerformanceDTO> coursePerformances) {
            this.coursePerformances = coursePerformances;
            return this;
        }

        public Builder newEnrollmentsThisWeek(Integer newEnrollmentsThisWeek) {
            this.newEnrollmentsThisWeek = newEnrollmentsThisWeek;
            return this;
        }

        public Builder newReviewsThisWeek(Integer newReviewsThisWeek) {
            this.newReviewsThisWeek = newReviewsThisWeek;
            return this;
        }

        public InstructorDashboardResponse build() {
            InstructorDashboardResponse response = new InstructorDashboardResponse();
            response.setInstructorId(instructorId);
            response.setInstructorName(instructorName);
            response.setTotalCourses(totalCourses);
            response.setPublishedCourses(publishedCourses);
            response.setTotalStudents(totalStudents);
            response.setTotalEnrollments(totalEnrollments);
            response.setOverallAverageRating(overallAverageRating);
            response.setTotalReviews(totalReviews);
            response.setActiveStudents(activeStudents);
            response.setAverageCompletionRate(averageCompletionRate);
            response.setTotalQuizAttempts(totalQuizAttempts);
            response.setCoursePerformances(coursePerformances);
            response.setNewEnrollmentsThisWeek(newEnrollmentsThisWeek);
            response.setNewReviewsThisWeek(newReviewsThisWeek);
            return response;
        }
    }

    public Long getInstructorId() {
        return instructorId;
    }

    public void setInstructorId(Long instructorId) {
        this.instructorId = instructorId;
    }

    public String getInstructorName() {
        return instructorName;
    }

    public void setInstructorName(String instructorName) {
        this.instructorName = instructorName;
    }

    public Integer getTotalCourses() {
        return totalCourses;
    }

    public void setTotalCourses(Integer totalCourses) {
        this.totalCourses = totalCourses;
    }

    public Integer getPublishedCourses() {
        return publishedCourses;
    }

    public void setPublishedCourses(Integer publishedCourses) {
        this.publishedCourses = publishedCourses;
    }

    public Integer getTotalStudents() {
        return totalStudents;
    }

    public void setTotalStudents(Integer totalStudents) {
        this.totalStudents = totalStudents;
    }

    public Integer getTotalEnrollments() {
        return totalEnrollments;
    }

    public void setTotalEnrollments(Integer totalEnrollments) {
        this.totalEnrollments = totalEnrollments;
    }

    public Double getOverallAverageRating() {
        return overallAverageRating;
    }

    public void setOverallAverageRating(Double overallAverageRating) {
        this.overallAverageRating = overallAverageRating;
    }

    public Integer getTotalReviews() {
        return totalReviews;
    }

    public void setTotalReviews(Integer totalReviews) {
        this.totalReviews = totalReviews;
    }

    public Integer getActiveStudents() {
        return activeStudents;
    }

    public void setActiveStudents(Integer activeStudents) {
        this.activeStudents = activeStudents;
    }

    public Double getAverageCompletionRate() {
        return averageCompletionRate;
    }

    public void setAverageCompletionRate(Double averageCompletionRate) {
        this.averageCompletionRate = averageCompletionRate;
    }

    public Integer getTotalQuizAttempts() {
        return totalQuizAttempts;
    }

    public void setTotalQuizAttempts(Integer totalQuizAttempts) {
        this.totalQuizAttempts = totalQuizAttempts;
    }

    public List<CoursePerformanceDTO> getCoursePerformances() {
        return coursePerformances;
    }

    public void setCoursePerformances(List<CoursePerformanceDTO> coursePerformances) {
        this.coursePerformances = coursePerformances;
    }

    public Integer getNewEnrollmentsThisWeek() {
        return newEnrollmentsThisWeek;
    }

    public void setNewEnrollmentsThisWeek(Integer newEnrollmentsThisWeek) {
        this.newEnrollmentsThisWeek = newEnrollmentsThisWeek;
    }

    public Integer getNewReviewsThisWeek() {
        return newReviewsThisWeek;
    }

    public void setNewReviewsThisWeek(Integer newReviewsThisWeek) {
        this.newReviewsThisWeek = newReviewsThisWeek;
    }
}
