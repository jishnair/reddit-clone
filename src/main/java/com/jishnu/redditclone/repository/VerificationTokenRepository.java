package com.jishnu.redditclone.repository;

import org.springframework.stereotype.Repository;

import com.jishnu.redditclone.model.VerificationToken;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {
    
}
