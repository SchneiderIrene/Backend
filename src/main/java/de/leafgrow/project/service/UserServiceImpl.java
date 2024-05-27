package de.leafgrow.project.service;


import de.leafgrow.project.domain.dto.UserRegistrationDto;
import de.leafgrow.project.domain.entity.Role;
import de.leafgrow.project.domain.entity.User;
import de.leafgrow.project.repository.RoleRepository;
import de.leafgrow.project.repository.UserRepository;
import de.leafgrow.project.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), new ArrayList<>());
    }

    @Override
    public User registerUser(UserRegistrationDto userRegistrationDto) {
        User user = new User();
        user.setUsername(userRegistrationDto.getUsername());
        user.setEmail(userRegistrationDto.getEmail());
        user.setPassword(passwordEncoder.encode(userRegistrationDto.getPassword()));

        Set<Role> userRole = (Set<Role>) roleRepository.findByName("ROLE_CUSTOMER");
        user.setRoles(userRole);

        return userRepository.save(user);
    }


    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User loadUserByEmail(String email) {
        if (userRepository.findByEmail(email) != null) {
            return userRepository.findByEmail(email);
        } else {

            throw new UsernameNotFoundException("User not found with email: " + email);
        }

    }


/*    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        if (userRepository.findByEmail(username) != null) {
            return (UserDetails) userRepository.findByEmail(username);
        } else {
            throw new UsernameNotFoundException("User not found with email: " + username));

        }

    }*/


}

