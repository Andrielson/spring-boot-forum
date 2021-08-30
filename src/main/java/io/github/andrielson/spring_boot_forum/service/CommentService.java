package io.github.andrielson.spring_boot_forum.service;

import io.github.andrielson.spring_boot_forum.dto.CommentDto;
import io.github.andrielson.spring_boot_forum.exceptions.PostNotFoundException;
import io.github.andrielson.spring_boot_forum.mapper.CommentMapper;
import io.github.andrielson.spring_boot_forum.model.NotificationEmail;
import io.github.andrielson.spring_boot_forum.model.User;
import io.github.andrielson.spring_boot_forum.repository.CommentRepository;
import io.github.andrielson.spring_boot_forum.repository.PostRepository;
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
@RequiredArgsConstructor
public class CommentService {

    private static final String POST_URL = "";
    private final AuthService authService;
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final MailContentBuilder mailContentBuilder;
    private final MailService mailService;

    @Transactional
    public CommentDto save(CommentDto commentDto) {
        var post = postRepository.findById(commentDto.getPostId())
                .orElseThrow(() -> new PostNotFoundException(commentDto.getPostId().toString()));
        var comment = commentMapper.map(commentDto, post, authService.getCurrentUser());
        commentRepository.save(comment);

        var message = mailContentBuilder.build(post.getUser().getUsername() + " posted a comment on your post." + POST_URL);
        sendCommentNotification(message, post.getUser());

        return commentMapper.mapToDto(comment);
    }

    private void sendCommentNotification(String message, User user) {
        mailService.sendMail(new NotificationEmail(user.getUsername() + " Commented on your post", user.getEmail(), message));
    }

    public List<CommentDto> getAllCommentsForPost(Long postId) {
        var post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException(postId.toString()));
        return commentRepository.findAllByPost(post)
                .stream()
                .map(commentMapper::mapToDto)
                .collect(Collectors.toList());
    }

    public List<CommentDto> getAllCommentsForUser(String username) {
        var user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
        return commentRepository.findAllByUser(user)
                .stream()
                .map(commentMapper::mapToDto)
                .collect(Collectors.toList());
    }
}
