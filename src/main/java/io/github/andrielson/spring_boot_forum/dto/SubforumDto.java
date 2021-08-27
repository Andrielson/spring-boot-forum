package io.github.andrielson.spring_boot_forum.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubforumDto {
    private Long id;
    private String name;
    private String description;
    private Integer numberOfPosts;
}
