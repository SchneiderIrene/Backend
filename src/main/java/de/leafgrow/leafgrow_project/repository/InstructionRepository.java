package de.leafgrow.leafgrow_project.repository;

import de.leafgrow.leafgrow_project.domain.entity.Instruction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface InstructionRepository extends JpaRepository<Instruction, Long> {
    Instruction findByDay(LocalDateTime day);
}
