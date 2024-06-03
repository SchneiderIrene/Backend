package de.leafgrow.leafgrow_project.service.interfaces;

import de.leafgrow.leafgrow_project.domain.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    void register(User user);
    User loadUserByEmail(String email);
    void save(User user);
    void delete(User user);
    void deleteConfirmCodesByUser(User user);
}
