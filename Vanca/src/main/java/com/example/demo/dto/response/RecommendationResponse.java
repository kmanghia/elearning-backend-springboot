package com.example.demo.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecommendationResponse {
    private List<CourseRecommendation> recommendations;
    private String recommendationType; // PERSONALIZED, POPULAR, SIMILAR
    private Integer totalResults;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
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
    }
}
