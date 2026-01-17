package com.example.demo.dto.response;

public class CoursePerformanceDTO {
    private Long courseId;
    private String courseName;
    private Integer totalEnrollments;
    private Integer completedEnrollments;
    private Double completionRate;
    private Double averageRating;
    private Integer totalReviews;
    private Integer totalLessons;
    private Integer totalQuizzes;
    private Double averageQuizScore;
    private Double averageProgressPercentage;

    public CoursePerformanceDTO() {
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long courseId;
        private String courseName;
        private Integer totalEnrollments;
        private Integer completedEnrollments;
        private Double completionRate;
        private Double averageRating;
        private Integer totalReviews;
        private Integer totalLessons;
        private Integer totalQuizzes;
        private Double averageQuizScore;
        private Double averageProgressPercentage;

        public Builder courseId(Long courseId) {
            this.courseId = courseId;
            return this;
        }

        public Builder courseName(String courseName) {
            this.courseName = courseName;
            return this;
        }

        public Builder totalEnrollments(Integer totalEnrollments) {
            this.totalEnrollments = totalEnrollments;
            return this;
        }

        public Builder completedEnrollments(Integer completedEnrollments) {
            this.completedEnrollments = completedEnrollments;
            return this;
        }

        public Builder completionRate(Double completionRate) {
            this.completionRate = completionRate;
            return this;
        }

        public Builder averageRating(Double averageRating) {
            this.averageRating = averageRating;
            return this;
        }

        public Builder totalReviews(Integer totalReviews) {
            this.totalReviews = totalReviews;
            return this;
        }

        public Builder totalLessons(Integer totalLessons) {
            this.totalLessons = totalLessons;
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

        public Builder averageProgressPercentage(Double averageProgressPercentage) {
            this.averageProgressPercentage = averageProgressPercentage;
            return this;
        }

        public CoursePerformanceDTO build() {
            CoursePerformanceDTO dto = new CoursePerformanceDTO();
            dto.setCourseId(courseId);
            dto.setCourseName(courseName);
            dto.setTotalEnrollments(totalEnrollments);
            dto.setCompletedEnrollments(completedEnrollments);
            dto.setCompletionRate(completionRate);
            dto.setAverageRating(averageRating);
            dto.setTotalReviews(totalReviews);
            dto.setTotalLessons(totalLessons);
            dto.setTotalQuizzes(totalQuizzes);
            dto.setAverageQuizScore(averageQuizScore);
            dto.setAverageProgressPercentage(averageProgressPercentage);
            return dto;
        }
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public Integer getTotalEnrollments() {
        return totalEnrollments;
    }

    public void setTotalEnrollments(Integer totalEnrollments) {
        this.totalEnrollments = totalEnrollments;
    }

    public Integer getCompletedEnrollments() {
        return completedEnrollments;
    }

    public void setCompletedEnrollments(Integer completedEnrollments) {
        this.completedEnrollments = completedEnrollments;
    }

    public Double getCompletionRate() {
        return completionRate;
    }

    public void setCompletionRate(Double completionRate) {
        this.completionRate = completionRate;
    }

    public Double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(Double averageRating) {
        this.averageRating = averageRating;
    }

    public Integer getTotalReviews() {
        return totalReviews;
    }

    public void setTotalReviews(Integer totalReviews) {
        this.totalReviews = totalReviews;
    }

    public Integer getTotalLessons() {
        return totalLessons;
    }

    public void setTotalLessons(Integer totalLessons) {
        this.totalLessons = totalLessons;
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

    public Double getAverageProgressPercentage() {
        return averageProgressPercentage;
    }

    public void setAverageProgressPercentage(Double averageProgressPercentage) {
        this.averageProgressPercentage = averageProgressPercentage;
    }
}
