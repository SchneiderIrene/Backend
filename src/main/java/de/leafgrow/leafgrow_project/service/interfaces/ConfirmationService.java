package de.leafgrow.leafgrow_project.service.interfaces;

import de.leafgrow.leafgrow_project.domain.entity.User;

public interface ConfirmationService {
    String generateConfirmationCode(User user);
    User confirmUser(String code);
}
