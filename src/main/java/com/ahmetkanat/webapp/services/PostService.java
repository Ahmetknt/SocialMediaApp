package com.ahmetkanat.webapp.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ahmetkanat.webapp.entities.Post;
import com.ahmetkanat.webapp.entities.User;
import com.ahmetkanat.webapp.repos.PostRepository;
import com.ahmetkanat.webapp.requests.PostCreateRequest;
import com.ahmetkanat.webapp.requests.PostUpdateRequest;
import com.ahmetkanat.webapp.responses.LikeResponse;
import com.ahmetkanat.webapp.responses.PostResponse;

@Service
public class PostService {

	private PostRepository postRepository;
	private LikeService likeService;
	private UserService userService;

	public PostService(PostRepository postRepository,UserService userService) {
		this.postRepository = postRepository;
		this.userService = userService;
	}
	
	@Autowired
	public void setLikeService(LikeService likeService) {
		this.likeService = likeService;
	}


	public List<PostResponse> getAllPosts(Optional<Long> userId) {
		
		List<Post> list;
		
		if(userId.isPresent()) {
			list = postRepository.findByUserId(userId.get()); 
		}else {
			list = postRepository.findAll();
		}
		
		return list.stream().map(p ->{ 
			
			List<LikeResponse> likes = likeService.getAllLikes(Optional.ofNullable(null), Optional.of(p.getId()));
			return new PostResponse(p, likes);}).collect(Collectors.toList());
		
	}
	
	public Post getPostById(Long postId) {
		return postRepository.findById(postId).orElse(null);
	}
	
	public PostResponse getOnePostByIdWithLikes(Long postId) {
		Post post = postRepository.findById(postId).orElse(null);
		List<LikeResponse> likes = likeService.getAllLikes(Optional.ofNullable(null), Optional.of(postId));
		return new PostResponse(post, likes); 
	}

	public Post createPost(PostCreateRequest newPostRequest) {
		
		User user = userService.getUserById(newPostRequest.getUserId());
		if(user == null)
			return null;
		
		Post toSave = new Post();
		toSave.setId(newPostRequest.getId());
		toSave.setText(newPostRequest.getText());
		toSave.setTitle(newPostRequest.getTitle());
		toSave.setCreateDate(new Date());
		toSave.setUser(user);
		
		return postRepository.save(toSave);
		
		
	}


	public Post updatePost(Long postId,PostUpdateRequest updatePost) {
		
		Optional<Post> post = postRepository.findById(postId);
		if(!post.isPresent())
			return null;
		
		Post toUpdate = post.get();
		toUpdate.setText(updatePost.getText());
		toUpdate.setTitle(updatePost.getTitle());
		return postRepository.save(toUpdate);
	}


	public void deletePostById(Long postId) {
		postRepository.deleteById(postId);
		
	}

	
}
