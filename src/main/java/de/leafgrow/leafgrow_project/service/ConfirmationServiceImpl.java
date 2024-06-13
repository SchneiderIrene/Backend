package de.leafgrow.leafgrow_project.service;

import de.leafgrow.leafgrow_project.domain.entity.ConfirmationCode;
import de.leafgrow.leafgrow_project.domain.entity.User;
import de.leafgrow.leafgrow_project.repository.ConfirmationCodeRepository;
import de.leafgrow.leafgrow_project.repository.UserRepository;
import de.leafgrow.leafgrow_project.service.interfaces.ConfirmationService;
import de.leafgrow.leafgrow_project.service.interfaces.PotService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class ConfirmationServiceImpl implements ConfirmationService {

    private ConfirmationCodeRepository repository;
    private UserRepository userRepository;
    private PotService potService;

    public ConfirmationServiceImpl(ConfirmationCodeRepository repository, UserRepository userRepository, PotService potService) {
        this.repository = repository;
        this.userRepository = userRepository;
        this.potService = potService;
    }

    @Override
    public User confirmUser(String code) {
        ConfirmationCode confirmationCode = repository.findByCode(code);
        if (confirmationCode == null || confirmationCode.getExpired().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Недействительный код подтверждения или истек срок его действия.");
        }

        User user = confirmationCode.getUser();
        user.setActive(true);
        //potService.createPotsForUser(user);
        userRepository.save(user);

        return user;
    }

    @Override
    public String generateConfirmationCode(User user) {
        LocalDateTime expired = LocalDateTime.now().plusMinutes(60);
        //LocalDateTime expired = LocalDateTime.now().plusMinutes(1);
        String code = UUID.randomUUID().toString();
        ConfirmationCode entity = new ConfirmationCode(code, user, expired);
        repository.save(entity);
        return code;
    }
}