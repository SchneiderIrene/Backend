package de.leafgrow.leafgrow_project.repository;

import de.leafgrow.leafgrow_project.domain.entity.Pot;
import de.leafgrow.leafgrow_project.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PotRepository extends JpaRepository<Pot, Long> {
    List<Pot> findByUser(User user);
}
