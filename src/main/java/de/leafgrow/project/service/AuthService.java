package de.leafgrow.project.service;

import de.leafgrow.project.dto.UserLoginDto;
import de.leafgrow.project.dto.UserRegistrationDto;
import de.leafgrow.project.model.User;
import de.leafgrow.project.repository.UserRepository;
import de.leafgrow.project.security.JwtTokenUtil;
import de.leafgrow.project.util.EmailUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private EmailUtil emailUtil;

    public String register(UserRegistrationDto userRegistrationDto) {
        if (userRepository.existsByEmail(userRegistrationDto.getEmail())) {
            throw new IllegalStateException("Email already in use");
        }
        User user = new User();
        user.setName(userRegistrationDto.getName());
        user.setEmail(userRegistrationDto.getEmail());
        user.setPassword(passwordEncoder.encode(userRegistrationDto.getPassword()));
        userRepository.save(user);
        emailUtil.sendRegistrationEmail(user.getEmail());
        return jwtTokenUtil.generateToken(userDetailsService.loadUserByUsername(user.getEmail()));
    }

    public String authenticate(UserLoginDto authenticationRequest) throws AuthenticationException {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(), authenticationRequest.getPassword()));
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getEmail());
        return jwtTokenUtil.generateToken(userDetails);
    }
}
