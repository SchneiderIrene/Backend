package de.leafgrow.leafgrow_project.repository;

import de.leafgrow.leafgrow_project.domain.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByTitle(String title);
}
