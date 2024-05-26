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

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User registerUser(UserRegistrationDto userRegistrationDto) {
        User user = new User();
        user.setName(userRegistrationDto.getName());
        user.setEmail(userRegistrationDto.getEmail());
        user.setPassword(passwordEncoder.encode(userRegistrationDto.getPassword()));

        Role userRole = roleRepository.findByName("ROLE_CUSTOMER");
        user.setRole(userRole);

        return userRepository.save(user);
    }
    

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    @Override
    public User loadUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
    }



    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return (UserDetails) userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + username));
    }


}

