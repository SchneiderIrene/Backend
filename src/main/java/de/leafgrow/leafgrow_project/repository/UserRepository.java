package de.leafgrow.leafgrow_project.repository;

import de.leafgrow.leafgrow_project.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
