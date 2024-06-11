package de.leafgrow.leafgrow_project.controller;

import de.leafgrow.leafgrow_project.domain.dto.PotDto;
import de.leafgrow.leafgrow_project.domain.entity.Instruction;
import de.leafgrow.leafgrow_project.domain.entity.Pot;
import de.leafgrow.leafgrow_project.domain.entity.User;
import de.leafgrow.leafgrow_project.repository.PotRepository;
import de.leafgrow.leafgrow_project.service.interfaces.PotService;
import de.leafgrow.leafgrow_project.service.interfaces.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PotControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private PotService potService;

    @Mock
    private PotRepository potRepository;

    @Mock
    private UserService userService;

    @InjectMocks
    private PotController potController;

    public PotControllerTest() {
        MockitoAnnotations.initMocks(this);
    }

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(potController).build();
    }

    @Test
    void getInstructionForPot() {
        Long potId = 1L;
        Pot pot = new Pot();
        pot.setInstruction(new Instruction());

        when(potRepository.findById(potId)).thenReturn(Optional.of(pot));

        ResponseEntity<Instruction> response =
                potController.getInstructionForPot(potId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void refreshPot() {
        Long potId = 1L;
        Pot pot = new Pot();

        when(potRepository.findById(potId)).thenReturn(Optional.of(pot));

        ResponseEntity<Void> response = potController.refreshPot(potId);

        verify(potService, times(1)).refreshPot(pot);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void createPotsForUser() {
        Authentication authentication = mock(Authentication.class);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        when(authentication.getName()).thenReturn("test@example.com");

        User user = new User();
        when(userService.loadUserByEmail("test@example.com")).thenReturn(user);

        ResponseEntity<Void> response = potController.createPotsForUser();

        verify(potService, times(1)).createPotsForUser(user);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void activatePot() {
        Long potId = 1L;

        ResponseEntity<Pot> response = potController.activatePot(potId);

        verify(potService, times(1)).activatePot(potId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void getPotsForUser() {
        Long userId = 1L;
        List<Pot> pots = List.of(new Pot(), new Pot());

        when(potService.findPotsByUserId(userId)).thenReturn(pots);

        ResponseEntity<List<PotDto>> response =
                potController.getPotsForUser();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }
}