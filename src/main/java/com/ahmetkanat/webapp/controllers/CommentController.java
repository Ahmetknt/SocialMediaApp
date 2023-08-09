package com.ahmetkanat.webapp.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ahmetkanat.webapp.entities.Comment;
import com.ahmetkanat.webapp.requests.CommentCreateRequest;
import com.ahmetkanat.webapp.requests.CommentUpdateRequest;
import com.ahmetkanat.webapp.responses.CommentResponse;
import com.ahmetkanat.webapp.services.CommentService;

@RestController
@RequestMapping("/comments")
public class CommentController {
	
	CommentService commentService;
	
	public CommentController(CommentService commentService) {
		this.commentService = commentService;
	}


	@GetMapping
	public List<CommentResponse> getAllComments(@RequestParam Optional<Long> postId,@RequestParam Optional<Long> userId){
		
		return commentService.getAllComments(postId,userId);
	}
	
	@GetMapping("/{commentId}")
	public Comment getComment(@PathVariable Long commentId) {
		return commentService.getComment(commentId);
	}
	
	@PostMapping()
	public Comment createComment(@RequestBody CommentCreateRequest request) {
		return commentService.createComment(request);
	}
	
	@PutMapping("/{commentId}")
	public Comment updateComment(@PathVariable Long commentId, @RequestBody CommentUpdateRequest request) {
		return commentService.updateCommentById(commentId,request);
	}
	
	@DeleteMapping("/{commentId}")
	public void deleteComment(@PathVariable Long commentId) {
		commentService.deleteCommentById(commentId);
	}
	

}
