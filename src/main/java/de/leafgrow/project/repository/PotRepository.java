package de.leafgrow.project.repository;

import de.leafgrow.project.domain.entity.Pot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PotRepository extends JpaRepository<Pot, Long> {
    List<Pot> findByUserId(Long userId);
}
