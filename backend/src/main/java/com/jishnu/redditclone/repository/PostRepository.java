package com.jishnu.redditclone.repository;

import java.util.List;

import com.jishnu.redditclone.model.Post;
import com.jishnu.redditclone.model.Subreddit;
import com.jishnu.redditclone.model.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllBySubreddit(Subreddit subreddit);

    List<Post> findByUser(User user);

}
