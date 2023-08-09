package com.ahmetkanat.webapp.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.ahmetkanat.webapp.entities.User;
import com.ahmetkanat.webapp.repos.CommentRepository;
import com.ahmetkanat.webapp.repos.LikeRepository;
import com.ahmetkanat.webapp.repos.PostRepository;
import com.ahmetkanat.webapp.repos.UserRepository;
import com.ahmetkanat.webapp.responses.UserResponse;

@Service
public class UserService {
	
	UserRepository userRepository;
	LikeRepository likeRepository;
	CommentRepository commentRepository;
	PostRepository postRepository;

	public UserService(UserRepository userRepository, LikeRepository likeRepository, 
			CommentRepository commentRepository, PostRepository postRepository) {
		this.userRepository = userRepository;
		this.likeRepository = likeRepository;
		this.commentRepository = commentRepository;
		this.postRepository = postRepository;
	}

	public List<User> getAllUsers() {
		return userRepository.findAll();
	}
	
	public User saveUser(User newUser) {
		return userRepository.save(newUser);
	}

	public User createUser(User newUser) {
		
		return userRepository.save(newUser);
	}

	public User getUserById(Long userId) {
		
		return userRepository.findById(userId).orElse(null);
	}

	public User updateUser(Long userId, User newUser) {
		
		Optional<User> user = userRepository.findById(userId);
		if(user.isPresent()) {
			User foundUser = user.get();
			foundUser.setUserName(newUser.getUserName());
			foundUser.setPassword(newUser.getPassword());
			foundUser.setAvatar(newUser.getAvatar());
			userRepository.save(foundUser);
			return foundUser;
		}else {
			return null;
		}
	}

	public void deleteById(Long userId) {
		userRepository.deleteById(userId);
		
	}

	public User getUserByName(String userName) {
		
		return userRepository.findByUserName(userName);
	}

	public List<Object> getUserActivity(Long userId) {
		
		List<Long> postIds = (postRepository.findTopByUserId(userId));
		if(postIds.isEmpty()) {
			return null;
		}
		
		List<Object> comments = commentRepository.findUserCommentsByPostId(postIds);
		List<Object> likes = likeRepository.findUserLikesByPostId(postIds);
		List<Object> result = new ArrayList<>();
		result.addAll(comments);
		result.addAll(likes);
		
		return result;
	}
	

}
