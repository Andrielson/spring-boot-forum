package io.github.andrielson.spring_boot_forum.mapper;

import io.github.andrielson.spring_boot_forum.dto.SubforumDto;
import io.github.andrielson.spring_boot_forum.model.Post;
import io.github.andrielson.spring_boot_forum.model.Subforum;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SubforumMapper {

    @Mapping(target = "numberOfPosts", expression = "java(mapPosts(subforum.getPosts()))")
    SubforumDto mapSubforumToDto(Subforum subforum);

    default Integer mapPosts(List<Post> numberOfPosts) {
        return numberOfPosts.size();
    }

    @InheritInverseConfiguration
    @Mapping(target = "posts", ignore = true)
    Subforum mapDtoToSubforum(SubforumDto subforumDto);
}
