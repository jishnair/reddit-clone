package com.jishnu.redditclone.service;

import java.util.List;
import java.util.stream.Collectors;

import com.jishnu.redditclone.dto.SubredditDto;
import com.jishnu.redditclone.exception.SpringRedditException;
import com.jishnu.redditclone.mapper.SubredditMapper;
import com.jishnu.redditclone.model.Subreddit;
import com.jishnu.redditclone.repository.SubredditRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
public class SubredditService {

    private final SubredditRepository subredditRepository;
    private final SubredditMapper subredditMapper;

    @Transactional
    public SubredditDto save(SubredditDto subredditDto) {
        Subreddit save = subredditRepository.save(subredditMapper.mapDtoToSubreddit(subredditDto));
        subredditDto.setId(save.getId());
        return subredditDto;
    }

    @Transactional(readOnly = true)
    public List<SubredditDto> getAll() {
        return subredditRepository.findAll().stream().map(subredditMapper::mapSubredditToDto)
                .collect(Collectors.toList());
    }

    public SubredditDto getSubreddit(Long id) {
        Subreddit subreddit = subredditRepository.findById(id)
                .orElseThrow(() -> new SpringRedditException("No subreddit with id=" + id));
        return subredditMapper.mapSubredditToDto(subreddit);
    }

}
