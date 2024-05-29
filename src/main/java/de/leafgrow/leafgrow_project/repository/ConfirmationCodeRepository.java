package de.leafgrow.leafgrow_project.repository;

import de.leafgrow.leafgrow_project.domain.entity.ConfirmationCode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConfirmationCodeRepository extends JpaRepository<ConfirmationCode, Long> {
    ConfirmationCode findByCode(String code);
}
