package com.jishnu.redditclone.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Vote {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long voteId;

    private VoteType voteType;

    @ManyToOne
    @JoinColumn(name = "postId", referencedColumnName = "postId")
    private Post post;

    @ManyToOne
    @JoinColumn(name = "userId", referencedColumnName = "userId")
    private User user;
    
}
