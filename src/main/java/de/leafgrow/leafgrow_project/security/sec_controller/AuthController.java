package de.leafgrow.leafgrow_project.security.sec_controller;

import de.leafgrow.leafgrow_project.domain.entity.User;
import de.leafgrow.leafgrow_project.security.sec_dto.LoginRequestDto;
import de.leafgrow.leafgrow_project.security.sec_dto.RefreshRequestDto;
import de.leafgrow.leafgrow_project.security.sec_dto.TokenResponseDto;
import de.leafgrow.leafgrow_project.security.sec_service.AuthService;
import de.leafgrow.leafgrow_project.service.interfaces.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private AuthService service;
    private UserService userService;

    public AuthController(AuthService service, UserService userService) {
        this.service = service;
        this.userService = userService;
    }

    @PostMapping("/login")
    @Operation(
            summary = "login",
            description = "Authenticated user login"
    )
    public ResponseEntity<Object> login(@RequestBody LoginRequestDto loginRequest, HttpServletResponse response) {
        try {
            User user = userService.loadUserByEmail(loginRequest.getEmail());
            if (user == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User not found");
            }

            if (!user.isActive()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Email not confirmed");
            }

            TokenResponseDto tokenDto = service.login(loginRequest);
            if (tokenDto == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to generate token");
            }

            Cookie cookie = new Cookie("Access-Token", tokenDto.getAccessToken());
            cookie.setPath("/");
            //http-only cookie
            cookie.setHttpOnly(true);
            response.addCookie(cookie);
            return ResponseEntity.ok(tokenDto);
        } catch (Exception e) {

            return new ResponseEntity<>(e.getMessage(), HttpStatus.I_AM_A_TEAPOT);

        }
    }

    @PostMapping("/access")
    @Operation(
            summary = "access",
            description = "Getting new access token"
    )
    public ResponseEntity<Object> getNewAccessToken(@RequestBody RefreshRequestDto request, HttpServletResponse response) {
        try {
            TokenResponseDto tokenDto = service.getAccessToken(request.getRefreshToken());
            Cookie cookie = new Cookie("Access-Token", tokenDto.getAccessToken()); // "access..." make a constant
            cookie.setPath("/");
            //http-only cookie
            cookie.setHttpOnly(true);
            response.addCookie(cookie);
            return ResponseEntity.ok(tokenDto);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/logout")
    @Operation(
            summary = "logout",
            description = "Authenticated user logout"
    )
    public ResponseEntity<User> logout(HttpServletResponse response) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        String email = authentication.getName();
        User user = userService.loadUserByEmail(email);

        Cookie cookie = new Cookie("Access-Token", null);
        cookie.setPath("/");
        //http-only cookie
        cookie.setHttpOnly(true);
        cookie.setMaxAge(0);
        response.addCookie(cookie);

        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}