package com.example.demo.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentResponse {
	
	private Long id;
	private Long postId;
	private Long authorId;
	private String authorName;
	private Long parentCommentId;
	private String content;
	private Boolean isAcceptedAnswer;
	private Integer voteCount;
	private List<CommentResponse> replies;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
}
