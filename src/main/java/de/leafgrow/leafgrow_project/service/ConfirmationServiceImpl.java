package de.leafgrow.leafgrow_project.service;

import de.leafgrow.leafgrow_project.domain.entity.ConfirmationCode;
import de.leafgrow.leafgrow_project.domain.entity.User;
import de.leafgrow.leafgrow_project.repository.ConfirmationCodeRepository;
import de.leafgrow.leafgrow_project.service.interfaces.ConfirmationService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class ConfirmationServiceImpl implements ConfirmationService {

    private ConfirmationCodeRepository repository;

    public ConfirmationServiceImpl(ConfirmationCodeRepository repository) {
        this.repository = repository;
    }

    @Override
    public String generateConfirmationCode(User user) {
        LocalDateTime expired = LocalDateTime.now().plusMinutes(10);
        String code = UUID.randomUUID().toString();
        ConfirmationCode entity = new ConfirmationCode(code, user, expired);
        repository.save(entity);
        return code;
    }
}
