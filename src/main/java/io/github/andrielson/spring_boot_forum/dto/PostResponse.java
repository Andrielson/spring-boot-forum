package io.github.andrielson.spring_boot_forum.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostResponse {
    private Long id;
    private String postName;
    private String url;
    private String description;
    private String username;
    private String subforumName;
    private Integer voteCount;
    private Integer commentCount;
    private String duration;
}
