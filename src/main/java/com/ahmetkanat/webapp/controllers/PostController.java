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

import com.ahmetkanat.webapp.entities.Post;
import com.ahmetkanat.webapp.requests.PostCreateRequest;
import com.ahmetkanat.webapp.requests.PostUpdateRequest;
import com.ahmetkanat.webapp.responses.PostResponse;
import com.ahmetkanat.webapp.responses.PostResponse;
import com.ahmetkanat.webapp.services.PostService;

@RestController
@RequestMapping("/posts")
public class PostController {
	
	private PostService postService;

	public PostController(PostService postService) {
		this.postService = postService;
	}
	
	@GetMapping
	public List<PostResponse> getAllPosts(@RequestParam Optional<Long> userId){
		return postService.getAllPosts(userId);
	}
	
	@PostMapping
	public Post createPost(@RequestBody PostCreateRequest newPostRequest) {
		return postService.createPost(newPostRequest);
	}
	
	@GetMapping("/{postId}")
	public PostResponse getPost(@PathVariable Long postId) {
		return postService.getOnePostByIdWithLikes(postId);
	}
	
	@PutMapping("/{postId}")
	public Post updatePost(@PathVariable Long postId,@RequestBody PostUpdateRequest updatePost) {
		return postService.updatePost(postId,updatePost);
	}
	
	@DeleteMapping("/{postId}")
	public void deletePost(@PathVariable Long postId) {
		postService.deletePostById(postId);
	}
	
	
	
	
}
