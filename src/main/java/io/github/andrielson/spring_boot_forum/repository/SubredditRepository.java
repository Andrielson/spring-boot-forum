package io.github.andrielson.spring_boot_forum.repository;

import io.github.andrielson.spring_boot_forum.model.Subforum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubredditRepository extends JpaRepository<Subforum, Long> {
}
