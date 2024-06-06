package de.leafgrow.leafgrow_project.controller;

import de.leafgrow.leafgrow_project.domain.entity.User;
import de.leafgrow.leafgrow_project.exception_handling.Response;
import de.leafgrow.leafgrow_project.security.sec_dto.ChangePasswordRequestDto;
import de.leafgrow.leafgrow_project.service.interfaces.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "UserController", description = "Controller for some operation with user.")
public class UserController {
    private UserService service;
    private BCryptPasswordEncoder encoder;

    public UserController(UserService service, BCryptPasswordEncoder encoder) {
        this.service = service;
        this.encoder = encoder;
    }

    @GetMapping("/profile")
    @Operation(
            summary = "get user info",
            description = "Receiving info about current user"
    )
    public ResponseEntity<User> getUserInfo() {

        //  Fix метод getUserInfo для использования информации проверенного пользователя.
        //  Удаляем параметр @RequestBody, поскольку email должен быть получен из контекста аутентификации.

        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userContent = authentication.getName();
            User user = service.loadUserByEmail(userContent);
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @DeleteMapping("/profile/delete-user")
    @Operation(
            summary = "delete user",
            description = "Deleting current user"
    )
    public ResponseEntity<Response> deleteUser(){
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String email = authentication.getName();

            User user = service.loadUserByEmail(email);

            if(user == null){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND,"User not found." );
            }

            service.delete(user);
            return ResponseEntity.ok(new Response("User was successfully deleted"));
        } catch (ResponseStatusException e){
            throw e;
        }
    }

    @PatchMapping("/profile/change-password")
    @Operation(
            summary = "change user password",
            description = "Changing current user's password"
    )
    public ResponseEntity<Response> changeUserPassword(@RequestBody ChangePasswordRequestDto newPassword) {

        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String email = authentication.getName();

            User user = service.loadUserByEmail(email);
            if (user == null) {
                return ResponseEntity.status(404).body(new Response("User not found"));
            }

            user.setPassword(encoder.encode(newPassword.getNewPassword()));
            service.save(user);

            return ResponseEntity.ok(new Response("Password was successfully changed"));
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
