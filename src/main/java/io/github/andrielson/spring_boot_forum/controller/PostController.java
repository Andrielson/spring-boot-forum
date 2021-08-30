package io.github.andrielson.spring_boot_forum.controller;

import io.github.andrielson.spring_boot_forum.dto.PostRequest;
import io.github.andrielson.spring_boot_forum.dto.PostResponse;
import io.github.andrielson.spring_boot_forum.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostController {
    private final PostService postService;

    @PostMapping
    public ResponseEntity<PostResponse> createPost(@RequestBody PostRequest postRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(postService.save(postRequest));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> getPost(@PathVariable Long id) {
        return ResponseEntity.ok(postService.getPost(id));
    }

    @GetMapping
    public ResponseEntity<List<PostResponse>> getAllPosts() {
        return ResponseEntity.ok(postService.getAllPosts());
    }

    @GetMapping("/by-subforum/{id}")
    public ResponseEntity<List<PostResponse>> getPostsBySubforum(@PathVariable Long id) {
        return ResponseEntity.ok(postService.getPostsBySubforum(id));
    }

    @GetMapping("/by-user/{name}")
    public ResponseEntity<List<PostResponse>> getPostsByUsername(@PathVariable String username) {
        return ResponseEntity.ok(postService.getPostsByUsername(username));
    }
}
