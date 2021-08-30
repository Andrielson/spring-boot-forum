package io.github.andrielson.spring_boot_forum.service;

import io.github.andrielson.spring_boot_forum.dto.PostRequest;
import io.github.andrielson.spring_boot_forum.dto.PostResponse;
import io.github.andrielson.spring_boot_forum.exceptions.PostNotFoundException;
import io.github.andrielson.spring_boot_forum.exceptions.SubforumNotFoundException;
import io.github.andrielson.spring_boot_forum.mapper.PostMapper;
import io.github.andrielson.spring_boot_forum.model.Post;
import io.github.andrielson.spring_boot_forum.repository.PostRepository;
import io.github.andrielson.spring_boot_forum.repository.SubforumRepository;
import io.github.andrielson.spring_boot_forum.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class PostService {

    private final AuthService authService;
    private final PostRepository postRepository;
    private final PostMapper postMapper;
    private final SubforumRepository subforumRepository;
    private final UserRepository userRepository;

    public PostResponse save(PostRequest postRequest) {
        var subforum = subforumRepository.findByName(postRequest.getSubforumName())
                .orElseThrow(() -> new SubforumNotFoundException(postRequest.getSubforumName()));
        var post = postRepository.save(postMapper.map(postRequest, subforum, authService.getCurrentUser()));
        return postMapper.mapToDto(post);
    }

    @Transactional(readOnly = true)
    public PostResponse getPost(Long id) {
        var post = postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException(id.toString()));
        return postMapper.mapToDto(post);
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getAllPosts() {
        return postRepository.findAll()
                .stream()
                .map(postMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getPostsBySubforum(Long subforumId) {
        var subforum = subforumRepository.findById(subforumId)
                .orElseThrow(() -> new SubforumNotFoundException(subforumId.toString()));
        return postRepository.findAllBySubforum(subforum)
                .stream()
                .map(postMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getPostsByUsername(String username) {
        var user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
        return postRepository.findAllByUser(user)
                .stream()
                .map(postMapper::mapToDto)
                .collect(Collectors.toList());
    }

}
