package de.leafgrow.project.service.interfaces;


import de.leafgrow.project.domain.entity.User;

public interface UserService {
    User registerUser(UserRegistrationDto userRegistrationDto);
    User findByEmail(String email);

    User loadUserByEmail(String email);
}
