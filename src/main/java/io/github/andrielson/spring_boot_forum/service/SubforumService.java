package io.github.andrielson.spring_boot_forum.service;

import io.github.andrielson.spring_boot_forum.dto.SubforumDto;
import io.github.andrielson.spring_boot_forum.exceptions.ForumException;
import io.github.andrielson.spring_boot_forum.mapper.SubforumMapper;
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
    private final SubforumMapper subforumMapper;

    @Transactional
    public @NonNull
    SubforumDto save(@NonNull SubforumDto subforumDto) {
        var subforum = subforumRepository.save(subforumMapper.mapDtoToSubforum(subforumDto));
        subforumDto.setId(subforum.getId());
        return subforumDto;
    }

    @Transactional(readOnly = true)
    public List<SubforumDto> getAll() {
        return subforumRepository.findAll()
                .stream()
                .map(subforumMapper::mapSubforumToDto)
                .collect(Collectors.toList());
    }

    public SubforumDto getSubforum(Long id) {
        var subforum = subforumRepository.findById(id)
                .orElseThrow(() -> new ForumException("No subforum found with ID"));
        return subforumMapper.mapSubforumToDto(subforum);
    }
}
