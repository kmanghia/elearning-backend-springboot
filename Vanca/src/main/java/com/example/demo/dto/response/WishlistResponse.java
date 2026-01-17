package com.example.demo.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WishlistResponse {
	private Long id;
	private Long courseId;
	private String courseTitle;
	private String courseDescription;
	private String courseThumbnailUrl;
	private BigDecimal coursePrice;
	private String instructorName;
	private LocalDateTime addedAt;
	private Boolean isEnrolled;
}
