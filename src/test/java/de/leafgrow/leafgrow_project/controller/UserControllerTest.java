package de.leafgrow.leafgrow_project.controller;

import de.leafgrow.leafgrow_project.domain.entity.User;
import de.leafgrow.leafgrow_project.exception_handling.Response;
import de.leafgrow.leafgrow_project.security.sec_dto.ChangePasswordRequestDto;
import de.leafgrow.leafgrow_project.service.interfaces.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class UserControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private BCryptPasswordEncoder encoder;

    @InjectMocks
    private UserController userController;

    @Mock
    private Authentication authentication;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Test
    void testGetUserInfo() {
        User mockUser = new User();
        mockUser.setEmail("leafgrow.project@gmail.com");

        when(authentication.getName()).thenReturn("leafgrow.project@gmail.com");
        when(userService.loadUserByEmail(anyString())).thenReturn(mockUser);

        ResponseEntity<User> response = userController.getUserInfo();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("leafgrow.project@gmail.com", response.getBody().getEmail());
    }

    @Test
    void testDeleteUser() {
        User mockUser = new User();
        mockUser.setEmail("leafgrow.project@gmail.com");

        when(authentication.getName()).thenReturn("leafgrow.project@gmail.com");
        when(userService.loadUserByEmail(anyString())).thenReturn(mockUser);

        ResponseEntity<Response> response = userController.deleteUser();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("User was successfully deleted", response.getBody().getMessage());
        verify(userService, times(1)).delete(mockUser);
    }

    @Test
    void testChangeUserPassword() {
        User mockUser = new User();
        mockUser.setEmail("leafgrow.project@gmail.com");
        ChangePasswordRequestDto passwordRequest = new ChangePasswordRequestDto();
        passwordRequest.setNewPassword("newPassword");

        when(authentication.getName()).thenReturn("leafgrow.project@gmail.com");
        when(userService.loadUserByEmail(anyString())).thenReturn(mockUser);
        when(encoder.encode(anyString())).thenReturn("encodedPassword");

        ResponseEntity<Response> response = userController.changeUserPassword(passwordRequest);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Password was successfully changed", response.getBody().getMessage());
        assertEquals("encodedPassword", mockUser.getPassword());
        verify(userService, times(1)).save(mockUser);
    }
}
