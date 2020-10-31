package com.jishnu.redditclone.repository;

import com.jishnu.redditclone.model.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long>{
    
}
