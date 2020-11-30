package com.jishnu.redditclone.service;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import com.jishnu.redditclone.dto.PostRequest;
import com.jishnu.redditclone.dto.PostResponse;
import com.jishnu.redditclone.exception.PostNotFoundException;
import com.jishnu.redditclone.exception.SubredditNotFoundException;
import com.jishnu.redditclone.exception.UsernameNotFoundException;
import com.jishnu.redditclone.mapper.PostMapper;
import com.jishnu.redditclone.model.Post;
import com.jishnu.redditclone.model.Subreddit;
import com.jishnu.redditclone.model.User;
import com.jishnu.redditclone.repository.PostRepository;
import com.jishnu.redditclone.repository.SubredditRepository;
import com.jishnu.redditclone.repository.UserRepository;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
@Slf4j
@Transactional
public class PostService {

	private final SubredditRepository subredditRepository;
	private final PostMapper postMapper;
	private final AuthService authService;
	private final PostRepository postRepository;
	private final UserRepository userRepository;

	public Post save(PostRequest postRequest) {
		Subreddit subreddit = subredditRepository.findByName(postRequest.getSubredditName())
				.orElseThrow(() -> new SubredditNotFoundException(postRequest.getSubredditName()));

		User currentUser = authService.getCurrentUser();
		return postMapper.map(postRequest, subreddit, currentUser);
	}

	@Transactional(readOnly = true)
	public PostResponse getPost(Long id) {
		Post post = postRepository.findById(id).orElseThrow(() -> new PostNotFoundException(id.toString()));
		return postMapper.mapToDto(post);
	}

	@Transactional(readOnly = true)
	public List<PostResponse> getAllPosts() {
		return postRepository.findAll().stream().map(postMapper::mapToDto).collect(toList());
	}

	@Transactional(readOnly = true)
	public List<PostResponse> getPostsBySubreddit(Long subredditId) {
		Subreddit subreddit = subredditRepository.findById(subredditId)
				.orElseThrow(() -> new SubredditNotFoundException(subredditId.toString()));
		List<Post> posts = postRepository.findAllBySubreddit(subreddit);
		return posts.stream().map(postMapper::mapToDto).collect(toList());
	}

	@Transactional(readOnly = true)
	public List<PostResponse> getPostsByUsername(String username) {
		User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
		return postRepository.findByUser(user).stream().map(postMapper::mapToDto).collect(toList());
	}

}
