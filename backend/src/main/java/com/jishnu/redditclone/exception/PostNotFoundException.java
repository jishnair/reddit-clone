package com.jishnu.redditclone.exception;

public class PostNotFoundException extends RuntimeException {

    public PostNotFoundException(String msg){
        super(msg);
    }
    
}
