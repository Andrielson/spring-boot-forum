package io.github.andrielson.spring_boot_forum.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostRequest {
    private Long postId;
    private String subforumName;
    private String postName;
    private String url;
    private String description;
}
