package com.ahmetkanat.webapp.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ahmetkanat.webapp.entities.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {

	List<Comment> findByPostIdAndUserId(Long postId, Long userId);

	List<Comment> findByUserId(Long userId);

	List<Comment> findByPostId(Long postId);
	
	@Query(value = "select 'commented on', c.post_id, u.avatar, u.user_name from "
			+ "comment c left join user u on u.id = c.user_id "
			+ "where c.post_id in :postIds limit 5", nativeQuery = true)
	List<Object> findUserCommentsByPostId(@Param("postIds") List<Long> postIds);

}
