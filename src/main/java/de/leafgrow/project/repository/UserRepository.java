package de.leafgrow.project.repository;

import de.leafgrow.project.domain.entity.User;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Bean
    User findByEmail(String email);
}
