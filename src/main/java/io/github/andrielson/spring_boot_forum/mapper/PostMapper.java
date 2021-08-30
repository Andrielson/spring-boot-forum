package io.github.andrielson.spring_boot_forum.mapper;

import io.github.andrielson.spring_boot_forum.dto.PostRequest;
import io.github.andrielson.spring_boot_forum.dto.PostResponse;
import io.github.andrielson.spring_boot_forum.model.Post;
import io.github.andrielson.spring_boot_forum.model.Subforum;
import io.github.andrielson.spring_boot_forum.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PostMapper {
    @Mapping(target = "createdDate", expression = "java(java.time.Instant.now())")
    @Mapping(target = "description", source = "postRequest.description")
    @Mapping(target = "user", source = "user")
    Post map(PostRequest postRequest, Subforum subforum, User user);

    @Mapping(target = "id", source = "postId")
    @Mapping(target = "subforumName", source = "subforum.name")
    @Mapping(target="userName", source = "user.username")
    PostResponse mapToDto(Post post);
}
