package com.ahmetkanat.webapp.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.ahmetkanat.webapp.entities.Comment;
import com.ahmetkanat.webapp.entities.Post;
import com.ahmetkanat.webapp.entities.User;
import com.ahmetkanat.webapp.repos.CommentRepository;
import com.ahmetkanat.webapp.requests.CommentCreateRequest;
import com.ahmetkanat.webapp.requests.CommentUpdateRequest;
import com.ahmetkanat.webapp.responses.CommentResponse;

@Service
public class CommentService {

	private CommentRepository commentRepository;
	private UserService userService;
	private PostService postService;
	
	

	public CommentService(CommentRepository commentRepository, UserService userService, PostService postService) {
		this.commentRepository = commentRepository;
		this.userService = userService;
		this.postService = postService;
	}

	public List<CommentResponse> getAllComments(Optional<Long> postId, Optional<Long> userId) {
		List<Comment> comments;
		if(userId.isPresent() && postId.isPresent()) {
			comments = commentRepository.findByPostIdAndUserId(postId.get(),userId.get());
		}else if(userId.isPresent()) {
			comments = commentRepository.findByUserId(userId.get());
		}else if(postId.isPresent()) {
			comments = commentRepository.findByPostId(postId.get());
		}else {
			comments = commentRepository.findAll();
		}
		return comments.stream().map(comment -> new CommentResponse(comment)).collect(Collectors.toList());
	
	}

	public Comment getComment(Long commentId) {
		return commentRepository.findById(commentId).orElse(null);
	}

	public Comment createComment(CommentCreateRequest request) {
		
		
		User user = userService.getUserById(request.getUserId());
		Post post = postService.getPostById(request.getPostId());
		if(user != null && post != null) {
			Comment commentToSave = new Comment();
			commentToSave.setId(request.getId());
			commentToSave.setPost(post);
			commentToSave.setUser(user);
			commentToSave.setText(request.getText());
			commentToSave.setCreateDate(new Date());
			return commentRepository.save(commentToSave);
		}else {
			return null;
		}
		
		
		
	}

	public Comment updateCommentById(Long commentId, CommentUpdateRequest request) {

		Optional<Comment> comment = commentRepository.findById(commentId);
		if(comment.isPresent()) {
			Comment commentToUpdate = comment.get();
			commentToUpdate.setText(request.getText());
			return commentRepository.save(commentToUpdate);
		}else {
			return null;
		}
		
	}

	public void deleteCommentById(Long commentId) {
		commentRepository.deleteById(commentId);
	}


	
	
	
}
