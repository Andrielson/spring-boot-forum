package io.github.andrielson.spring_boot_forum.service;

import io.github.andrielson.spring_boot_forum.dto.VoteDto;
import io.github.andrielson.spring_boot_forum.exceptions.ForumException;
import io.github.andrielson.spring_boot_forum.exceptions.PostNotFoundException;
import io.github.andrielson.spring_boot_forum.model.Post;
import io.github.andrielson.spring_boot_forum.model.Vote;
import io.github.andrielson.spring_boot_forum.model.VoteType;
import io.github.andrielson.spring_boot_forum.repository.PostRepository;
import io.github.andrielson.spring_boot_forum.repository.VoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class VoteService {
    private final VoteRepository voteRepository;
    private final PostRepository postRepository;
    private final AuthService authService;

    @Transactional
    public void vote(VoteDto voteDto) {
        var post = postRepository.findById(voteDto.getPostId())
                .orElseThrow(() -> new PostNotFoundException("Post not found with ID: " + voteDto.getPostId()));
        var voteByPostAndUser = voteRepository.findTopByPostAndUserOrderByVoteIdDesc(post, authService.getCurrentUser());
        if (voteByPostAndUser.isPresent() && voteByPostAndUser.get().getVoteType().equals(voteDto.getVoteType())) {
            throw new ForumException("You have already " + voteDto.getVoteType() + "'d for this posts");
        }
        post.setVoteCount(post.getVoteCount() + (VoteType.UPVOTE.equals(voteDto.getVoteType()) ? 1 : -1));
        voteRepository.save(mapToVote(voteDto, post));
        postRepository.save(post);
    }

    private Vote mapToVote(VoteDto voteDto, Post post) {
        return Vote.builder()
                .voteType(voteDto.getVoteType())
                .post(post)
                .user(authService.getCurrentUser())
                .build();
    }
}
