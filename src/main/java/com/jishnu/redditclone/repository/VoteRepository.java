package com.jishnu.redditclone.model.repository;

import com.jishnu.redditclone.model.Vote;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VoteRepository extends JpaRepository<Vote,Long>{
    
}
