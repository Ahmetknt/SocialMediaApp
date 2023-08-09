package com.ahmetkanat.webapp.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ahmetkanat.webapp.entities.RefreshToken;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long>{

	RefreshToken findByUserId(Long userId);
}
