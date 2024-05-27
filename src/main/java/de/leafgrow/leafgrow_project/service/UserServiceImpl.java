package de.leafgrow.leafgrow_project.service;

import de.leafgrow.leafgrow_project.domain.entity.User;
import de.leafgrow.leafgrow_project.repository.UserRepository;
import de.leafgrow.leafgrow_project.service.interfaces.EmailService;
import de.leafgrow.leafgrow_project.service.interfaces.RoleService;
import de.leafgrow.leafgrow_project.service.interfaces.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository repository;
    private BCryptPasswordEncoder encoder;
    private RoleService roleService;
    private EmailService emailService;

    public UserServiceImpl(UserRepository repository, BCryptPasswordEncoder encoder, RoleService roleService, EmailService emailService) {
        this.repository = repository;
        this.encoder = encoder;
        this.roleService = roleService;
        this.emailService = emailService;
    }

    @Override
    public void register(User user) {
        user.setId(null);
        user.setPassword(encoder.encode(user.getPassword()));
        user.setRoles(Set.of(roleService.getRoleUser()));
        user.setActive(false);

        repository.save(user);

        emailService.sendConfirmationEmail(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repository.findByUsername(username);
        if(user == null){
            throw new UsernameNotFoundException("User not found");
        }
        return user;
    }
}
