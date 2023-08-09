package com.ahmetkanat.webapp.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.ahmetkanat.webapp.entities.Like;
import com.ahmetkanat.webapp.entities.Post;
import com.ahmetkanat.webapp.entities.User;
import com.ahmetkanat.webapp.repos.LikeRepository;
import com.ahmetkanat.webapp.requests.LikeCreateRequest;
import com.ahmetkanat.webapp.responses.LikeResponse;

@Service
public class LikeService {
	
	private LikeRepository likeRepository;
	private UserService userService;
	private PostService postService;
	
	
	public LikeService(LikeRepository likeRepository, UserService userService, PostService postService) {
		this.likeRepository = likeRepository;
		this.userService = userService;
		this.postService = postService;
	}

	public List<LikeResponse> getAllLikes(Optional<Long> userId, Optional<Long> postId) {
		List<Like> list;
		if(userId.isPresent() && postId.isPresent()) {
			list = likeRepository.findByUserIdAndPostId(userId.get(), postId.get());
		}else if(userId.isPresent()) {
			list = likeRepository.findByUserId(userId.get());
		}else if(postId.isPresent()) {
			list = likeRepository.findByPostId(postId.get());
		}else
			list = likeRepository.findAll();
		return list.stream().map(like -> new LikeResponse(like)).collect(Collectors.toList());
	}
	public Like getLike(Long likeId) {
		return likeRepository.findById(likeId).orElse(null);
	}

	public Like createLike(LikeCreateRequest request) {
		
		User user = userService.getUserById(request.getUserId());
		Post post = postService.getPostById(request.getPostId());
		if(user != null && post != null) {
			Like likeToSave = new Like();
			likeToSave.setId(request.getId());
			likeToSave.setPost(post);
			likeToSave.setUser(user);
			return likeRepository.save(likeToSave);
		}else		
			return null;
	}

	public void deleteLikeById(Long likeId) {
		likeRepository.deleteById(likeId);
		
	}
	

}
