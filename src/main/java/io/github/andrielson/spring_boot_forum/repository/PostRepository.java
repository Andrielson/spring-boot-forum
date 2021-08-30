package io.github.andrielson.spring_boot_forum.repository;

import io.github.andrielson.spring_boot_forum.model.Post;
import io.github.andrielson.spring_boot_forum.model.Subforum;
import io.github.andrielson.spring_boot_forum.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllBySubforum(Subforum subforum);
    List<Post> findAllByUser(User user);
}
