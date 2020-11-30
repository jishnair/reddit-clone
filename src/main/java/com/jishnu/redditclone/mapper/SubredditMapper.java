package com.jishnu.redditclone.mapper;

import java.util.List;

import com.jishnu.redditclone.dto.SubredditDto;
import com.jishnu.redditclone.model.Post;
import com.jishnu.redditclone.model.Subreddit;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SubredditMapper {

    @Mapping(target = "numberOfPosts", expression = "java(mapPosts(subreddit.getPosts()))")
    SubredditDto mapSubredditToDto(Subreddit subreddit);

    default Integer mapPosts(List<Post> posts) {
        return posts.size();
    }

    @InheritInverseConfiguration
    @Mapping(target="posts", ignore = true)
    Subreddit mapDtoToSubreddit(SubredditDto subredditDto);

}
