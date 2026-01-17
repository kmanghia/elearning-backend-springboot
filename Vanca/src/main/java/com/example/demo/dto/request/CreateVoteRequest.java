package com.example.demo.dto.request;

import com.example.demo.model.Vote;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateVoteRequest {
	
	private Long postId;
	private Long commentId;
	
	@NotNull(message = "Vote type is required")
	private Vote.VoteType voteType;
}
