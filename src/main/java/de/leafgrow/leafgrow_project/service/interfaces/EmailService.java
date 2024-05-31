package de.leafgrow.leafgrow_project.service.interfaces;

import de.leafgrow.leafgrow_project.domain.entity.User;

public interface EmailService {
    void sendConfirmationEmail(User user);
}
