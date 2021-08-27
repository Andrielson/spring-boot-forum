package io.github.andrielson.spring_boot_forum.service;

import io.github.andrielson.spring_boot_forum.dto.SubforumDto;
import io.github.andrielson.spring_boot_forum.model.Subforum;
import io.github.andrielson.spring_boot_forum.repository.SubforumRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class SubforumService {

    private final SubforumRepository subforumRepository;

    @Transactional
    public @NonNull
    SubforumDto save(@NonNull SubforumDto subforumDto) {
        var subforum = subforumRepository.save(mapSubforumDto(subforumDto));
        subforumDto.setId(subforum.getId());
        return subforumDto;
    }

    @Transactional(readOnly = true)
    public List<SubforumDto> getAll() {
        return subforumRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    private @NonNull
    Subforum mapSubforumDto(@NonNull SubforumDto subforumDto) {
        return Subforum.builder()
                .name(subforumDto.getName())
                .description(subforumDto.getDescription())
                .build();
    }

    private @NonNull
    SubforumDto mapToDto(@NonNull Subforum subforum) {
        return SubforumDto.builder()
                .id(subforum.getId())
                .name(subforum.getName())
                .description(subforum.getDescription())
                .numberOfPosts(subforum.getPosts().size())
                .build();
    }
}
