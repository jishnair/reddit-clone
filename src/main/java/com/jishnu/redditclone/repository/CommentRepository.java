package com.jishnu.redditclone.repository;

import java.util.List;

import com.jishnu.redditclone.model.Comment;
import com.jishnu.redditclone.model.Post;
import com.jishnu.redditclone.model.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPost(Post post);

    List<Comment> findAllByUser(User user);

}
