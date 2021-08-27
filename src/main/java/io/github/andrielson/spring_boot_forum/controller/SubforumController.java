package io.github.andrielson.spring_boot_forum.controller;

import io.github.andrielson.spring_boot_forum.dto.SubforumDto;
import io.github.andrielson.spring_boot_forum.service.SubforumService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/subforums")
@RequiredArgsConstructor
public class SubforumController {

    private final SubforumService subforumService;

    @PostMapping
    public ResponseEntity<SubforumDto> createSubforum(@RequestBody SubforumDto subforumDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(subforumService.save(subforumDto));
    }

    @GetMapping
    public ResponseEntity<List<SubforumDto>> getAllSubforums() {
        return ResponseEntity.ok(subforumService.getAll());
    }
}
