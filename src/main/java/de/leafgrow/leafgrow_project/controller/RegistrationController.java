package de.leafgrow.leafgrow_project.controller;

import de.leafgrow.leafgrow_project.domain.entity.User;
import de.leafgrow.leafgrow_project.exception_handling.Response;
import de.leafgrow.leafgrow_project.service.interfaces.ConfirmationService;
import de.leafgrow.leafgrow_project.service.interfaces.EmailService;
import de.leafgrow.leafgrow_project.service.interfaces.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/register")
public class RegistrationController {

    private UserService userService;
    private ConfirmationService confirmationService;
    private EmailService emailService;


    public RegistrationController(UserService userService, ConfirmationService confirmationService, EmailService emailService) {
        this.userService = userService;
        this.confirmationService = confirmationService;
        this.emailService = emailService;
    }

    @PostMapping
    public Response register(@RequestBody User user) {
        try {
            userService.register(user);
            return new Response("Регистрация успешно завершена. Проверьте ваш email для завершения процесса.");
        } catch (Exception e) {
            return new Response("Ошибка при регистрации пользователя: " + e.getMessage());
        }
    }

    @GetMapping("/resent")
    public Response resendConfirmation(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        User user = userService.loadUserByEmail(email);
        emailService.sendConfirmationEmail(user);
        return new Response("Registration confirmation has been resent. Please check your mailbox.");
    }

    @GetMapping("/confirm")
    public Response confirmAccount(@RequestParam String code) {
        try {
            User confirmedUser = confirmationService.confirmUser(code);
            return new Response("Аккаунт успешно активирован. Теперь вы можете войти.");
        } catch (Exception e) {
            return new Response("Ошибка при подтверждении аккаунта: " + e.getMessage());
        }
    }


}
