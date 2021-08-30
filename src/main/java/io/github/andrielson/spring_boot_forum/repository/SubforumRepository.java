package io.github.andrielson.spring_boot_forum.repository;

import io.github.andrielson.spring_boot_forum.model.Subforum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SubforumRepository extends JpaRepository<Subforum, Long> {
    Optional<Subforum> findByName(String name);
}
