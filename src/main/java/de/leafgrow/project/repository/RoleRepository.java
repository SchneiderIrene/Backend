package de.leafgrow.project.repository;


import de.leafgrow.project.domain.entity.Role;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    @Bean
    Role findByName(String name);
}
