package io.github.andrielson.spring_boot_forum.model;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class NotificationEmail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long voteId;

    private VoteType voteType;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "postId", referencedColumnName = "postId")
    @ToString.Exclude
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", referencedColumnName = "userId")
    @ToString.Exclude
    private User user;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        NotificationEmail that = (NotificationEmail) o;

        return Objects.equals(voteId, that.voteId);
    }

    @Override
    public int hashCode() {
        return 1304623416;
    }
}
