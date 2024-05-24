package de.leafgrow.project.repository;

import de.leafgrow.project.model.Pot;
import de.leafgrow.project.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PotRepository extends JpaRepository<Pot, Long> {
    List<Pot> findAllByUser(User user);
}
