package com.example.demo.dto.response;

public class StudentEngagementMetrics {
    private Long courseId;
    private String courseName;
    private Integer totalStudents;
    private Integer activeStudents; // Students who made progress in last 7 days
    private Integer completedStudents;
    private Double completionRate; // Percentage
    private Double averageProgress; // Average progress percentage
    private Integer totalQuizAttempts;
    private Double averageQuizScore;

    public StudentEngagementMetrics() {
    }

    public StudentEngagementMetrics(Long courseId, String courseName, Integer totalStudents, Integer activeStudents,
            Integer completedStudents, Double completionRate, Double averageProgress,
            Integer totalQuizAttempts, Double averageQuizScore) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.totalStudents = totalStudents;
        this.activeStudents = activeStudents;
        this.completedStudents = completedStudents;
        this.completionRate = completionRate;
        this.averageProgress = averageProgress;
        this.totalQuizAttempts = totalQuizAttempts;
        this.averageQuizScore = averageQuizScore;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long courseId;
        private String courseName;
        private Integer totalStudents;
        private Integer activeStudents;
        private Integer completedStudents;
        private Double completionRate;
        private Double averageProgress;
        private Integer totalQuizAttempts;
        private Double averageQuizScore;

        public Builder courseId(Long courseId) {
            this.courseId = courseId;
            return this;
        }

        public Builder courseName(String courseName) {
            this.courseName = courseName;
            return this;
        }

        public Builder totalStudents(Integer totalStudents) {
            this.totalStudents = totalStudents;
            return this;
        }

        public Builder activeStudents(Integer activeStudents) {
            this.activeStudents = activeStudents;
            return this;
        }

        public Builder completedStudents(Integer completedStudents) {
            this.completedStudents = completedStudents;
            return this;
        }

        public Builder completionRate(Double completionRate) {
            this.completionRate = completionRate;
            return this;
        }

        public Builder averageProgress(Double averageProgress) {
            this.averageProgress = averageProgress;
            return this;
        }

        public Builder totalQuizAttempts(Integer totalQuizAttempts) {
            this.totalQuizAttempts = totalQuizAttempts;
            return this;
        }

        public Builder averageQuizScore(Double averageQuizScore) {
            this.averageQuizScore = averageQuizScore;
            return this;
        }

        public StudentEngagementMetrics build() {
            return new StudentEngagementMetrics(courseId, courseName, totalStudents, activeStudents,
                    completedStudents, completionRate, averageProgress, totalQuizAttempts, averageQuizScore);
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

    public Integer getTotalStudents() {
        return totalStudents;
    }

    public void setTotalStudents(Integer totalStudents) {
        this.totalStudents = totalStudents;
    }

    public Integer getActiveStudents() {
        return activeStudents;
    }

    public void setActiveStudents(Integer activeStudents) {
        this.activeStudents = activeStudents;
    }

    public Integer getCompletedStudents() {
        return completedStudents;
    }

    public void setCompletedStudents(Integer completedStudents) {
        this.completedStudents = completedStudents;
    }

    public Double getCompletionRate() {
        return completionRate;
    }

    public void setCompletionRate(Double completionRate) {
        this.completionRate = completionRate;
    }

    public Double getAverageProgress() {
        return averageProgress;
    }

    public void setAverageProgress(Double averageProgress) {
        this.averageProgress = averageProgress;
    }

    public Integer getTotalQuizAttempts() {
        return totalQuizAttempts;
    }

    public void setTotalQuizAttempts(Integer totalQuizAttempts) {
        this.totalQuizAttempts = totalQuizAttempts;
    }

    public Double getAverageQuizScore() {
        return averageQuizScore;
    }

    public void setAverageQuizScore(Double averageQuizScore) {
        this.averageQuizScore = averageQuizScore;
    }
}
