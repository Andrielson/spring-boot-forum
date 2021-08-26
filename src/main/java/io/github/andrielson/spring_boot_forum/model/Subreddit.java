package io.github.andrielson.spring_boot_forum.model;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.Instant;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Subreddit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Community name is required")
    private String name;

    @NotBlank(message = "Description is required")
    private String description;

    @OneToMany(fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<Post> posts;

    private Instant createdDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    private User user;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Subreddit subreddit = (Subreddit) o;

        return Objects.equals(id, subreddit.id);
    }

    @Override
    public int hashCode() {
        return 368711113;
    }
}
