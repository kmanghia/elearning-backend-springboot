package com.example.demo.dto.response;

import java.util.List;

public class RecommendationResponse {
    private List<CourseRecommendation> recommendations;
    private String recommendationType; // PERSONALIZED, POPULAR, SIMILAR
    private Integer totalResults;

    public RecommendationResponse() {
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private List<CourseRecommendation> recommendations;
        private String recommendationType;
        private Integer totalResults;

        public Builder recommendations(List<CourseRecommendation> recommendations) {
            this.recommendations = recommendations;
            return this;
        }

        public Builder recommendationType(String recommendationType) {
            this.recommendationType = recommendationType;
            return this;
        }

        public Builder totalResults(Integer totalResults) {
            this.totalResults = totalResults;
            return this;
        }

        public RecommendationResponse build() {
            RecommendationResponse response = new RecommendationResponse();
            response.setRecommendations(recommendations);
            response.setRecommendationType(recommendationType);
            response.setTotalResults(totalResults);
            return response;
        }
    }

    public List<CourseRecommendation> getRecommendations() {
        return recommendations;
    }

    public void setRecommendations(List<CourseRecommendation> recommendations) {
        this.recommendations = recommendations;
    }

    public String getRecommendationType() {
        return recommendationType;
    }

    public void setRecommendationType(String recommendationType) {
        this.recommendationType = recommendationType;
    }

    public Integer getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(Integer totalResults) {
        this.totalResults = totalResults;
    }

    // Nested class
    public static class CourseRecommendation {
        private Long courseId;
        private String title;
        private String description;
        private String thumbnailUrl;
        private String instructorName;
        private Double averageRating;
        private Integer enrollmentCount;
        private String categoryName;
        private String reason; // Why this course is recommended
        private Double relevanceScore; // 0.0 to 1.0

        public CourseRecommendation() {
        }

        public static Builder builder() {
            return new Builder();
        }

        public static class Builder {
            private Long courseId;
            private String title;
            private String description;
            private String thumbnailUrl;
            private String instructorName;
            private Double averageRating;
            private Integer enrollmentCount;
            private String categoryName;
            private String reason;
            private Double relevanceScore;

            public Builder courseId(Long courseId) {
                this.courseId = courseId;
                return this;
            }

            public Builder title(String title) {
                this.title = title;
                return this;
            }

            public Builder description(String description) {
                this.description = description;
                return this;
            }

            public Builder thumbnailUrl(String thumbnailUrl) {
                this.thumbnailUrl = thumbnailUrl;
                return this;
            }

            public Builder instructorName(String instructorName) {
                this.instructorName = instructorName;
                return this;
            }

            public Builder averageRating(Double averageRating) {
                this.averageRating = averageRating;
                return this;
            }

            public Builder enrollmentCount(Integer enrollmentCount) {
                this.enrollmentCount = enrollmentCount;
                return this;
            }

            public Builder categoryName(String categoryName) {
                this.categoryName = categoryName;
                return this;
            }

            public Builder reason(String reason) {
                this.reason = reason;
                return this;
            }

            public Builder relevanceScore(Double relevanceScore) {
                this.relevanceScore = relevanceScore;
                return this;
            }

            public CourseRecommendation build() {
                CourseRecommendation recommendation = new CourseRecommendation();
                recommendation.setCourseId(courseId);
                recommendation.setTitle(title);
                recommendation.setDescription(description);
                recommendation.setThumbnailUrl(thumbnailUrl);
                recommendation.setInstructorName(instructorName);
                recommendation.setAverageRating(averageRating);
                recommendation.setEnrollmentCount(enrollmentCount);
                recommendation.setCategoryName(categoryName);
                recommendation.setReason(reason);
                recommendation.setRelevanceScore(relevanceScore);
                return recommendation;
            }
        }

        public Long getCourseId() {
            return courseId;
        }

        public void setCourseId(Long courseId) {
            this.courseId = courseId;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getThumbnailUrl() {
            return thumbnailUrl;
        }

        public void setThumbnailUrl(String thumbnailUrl) {
            this.thumbnailUrl = thumbnailUrl;
        }

        public String getInstructorName() {
            return instructorName;
        }

        public void setInstructorName(String instructorName) {
            this.instructorName = instructorName;
        }

        public Double getAverageRating() {
            return averageRating;
        }

        public void setAverageRating(Double averageRating) {
            this.averageRating = averageRating;
        }

        public Integer getEnrollmentCount() {
            return enrollmentCount;
        }

        public void setEnrollmentCount(Integer enrollmentCount) {
            this.enrollmentCount = enrollmentCount;
        }

        public String getCategoryName() {
            return categoryName;
        }

        public void setCategoryName(String categoryName) {
            this.categoryName = categoryName;
        }

        public String getReason() {
            return reason;
        }

        public void setReason(String reason) {
            this.reason = reason;
        }

        public Double getRelevanceScore() {
            return relevanceScore;
        }

        public void setRelevanceScore(Double relevanceScore) {
            this.relevanceScore = relevanceScore;
        }
    }
}
