package com.example.demo.dto.response;

import com.example.demo.model.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostResponse {
	
	private Long id;
	private Long courseId;
	private String courseTitle;
	private Long authorId;
	private String authorName;
	private String title;
	private String content;
	private Post.PostType type;
	private Boolean isPinned;
	private Boolean isResolved;
	private Integer viewCount;
	private Long voteCount;
	private Integer commentCount;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
}
