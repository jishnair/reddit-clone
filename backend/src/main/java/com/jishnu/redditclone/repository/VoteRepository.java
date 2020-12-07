package com.jishnu.redditclone.repository;

import com.jishnu.redditclone.model.Post;
import com.jishnu.redditclone.model.User;
import com.jishnu.redditclone.model.Vote;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VoteRepository extends JpaRepository<Vote,Long>{
    Optional<Vote> findTopByPostAndUserOrderByVoteIdDesc(Post post, User currentUser);

}
