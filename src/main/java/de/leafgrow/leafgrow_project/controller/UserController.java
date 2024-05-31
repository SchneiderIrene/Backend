package de.leafgrow.leafgrow_project.controller;

import de.leafgrow.leafgrow_project.domain.entity.User;
import de.leafgrow.leafgrow_project.exception_handling.Response;
import de.leafgrow.leafgrow_project.service.interfaces.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class UserController {
    private UserService service;

    public UserController(UserService service) {
        this.service = service;
    }


    @GetMapping("/profile")
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

    @PatchMapping("/profile/change-password")
    public ResponseEntity<Response> changeUserPassword(@RequestBody String newPassword) {

        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String email = authentication.getName();

            User user = service.loadUserByEmail(email);
            user.setPassword(new BCryptPasswordEncoder().encode(newPassword));
            service.save(user);
            return ResponseEntity.ok(new Response("Password was successfully changed"));
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
