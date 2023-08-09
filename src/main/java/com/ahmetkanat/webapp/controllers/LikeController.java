package com.ahmetkanat.webapp.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ahmetkanat.webapp.entities.Like;
import com.ahmetkanat.webapp.requests.LikeCreateRequest;
import com.ahmetkanat.webapp.responses.LikeResponse;
import com.ahmetkanat.webapp.services.LikeService;

@RestController
@RequestMapping("/likes")
public class LikeController {

	LikeService likeService;

	public LikeController(LikeService likeService) {
		this.likeService = likeService;
	}
	
	@GetMapping
	public List<LikeResponse> getAllLikes(@RequestParam Optional<Long> userId,@RequestParam Optional<Long> postId){
		
		return likeService.getAllLikes(userId,postId);
	}
	
	@GetMapping("/{likeId}")
	public Like getLike(@PathVariable Long likeId) {
		return likeService.getLike(likeId);
	}
	@PostMapping()
	public Like createComment(@RequestBody LikeCreateRequest request) {
		return likeService.createLike(request);
	}
	
	
	@DeleteMapping("/{likeId}")
	public void deleteComment(@PathVariable Long likeId) {
		likeService.deleteLikeById(likeId);
	}
	
	
}
