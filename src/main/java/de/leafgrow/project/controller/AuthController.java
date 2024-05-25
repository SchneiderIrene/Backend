package de.leafgrow.project.controller;

import de.leafgrow.project.dto.UserLoginDto;
import de.leafgrow.project.dto.UserRegistrationDto;
import de.leafgrow.project.security.JwtTokenUtil;
import de.leafgrow.project.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserRegistrationDto userRegistrationDto) {
        return ResponseEntity.ok(userService.register(userRegistrationDto));
    }

    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody UserLoginDto authenticationRequest) throws Exception {
        return ResponseEntity.ok(userService.authenticate(authenticationRequest));
    }
}
