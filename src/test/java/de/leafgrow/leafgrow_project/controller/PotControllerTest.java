package de.leafgrow.leafgrow_project.controller;

import de.leafgrow.leafgrow_project.domain.entity.Instruction;
import de.leafgrow.leafgrow_project.domain.entity.Pot;
import de.leafgrow.leafgrow_project.domain.entity.User;
import de.leafgrow.leafgrow_project.repository.PotRepository;
import de.leafgrow.leafgrow_project.service.interfaces.PotService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PotControllerTest {

    @Mock
    private PotService service;

    @Mock
    private PotRepository repository;

    @InjectMocks
    private PotController controller;

    public PotControllerTest() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void getInstructionForPot() {
        Long potId = 1L;
        Pot pot = new Pot();
        pot.setInstruction(new Instruction());

        when(repository.findById(potId)).thenReturn(Optional.of(pot));

        ResponseEntity<Instruction> response = controller.getInstructionForPot(potId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void refreshPot() {
        Long potId = 1L;
        Pot pot = new Pot();

        when(repository.findById(potId)).thenReturn(Optional.of(pot));

        ResponseEntity<Void> response = controller.refreshPot(potId);

        verify(service, times(1)).refreshPot(pot);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void createPotsForUser() {
        User user = new User();

        ResponseEntity<Void> response = controller.createPotsForUser(user);

        verify(service, times(1)).createPotsForUser(user);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void activatePot() {
        Long potId = 1L;

        ResponseEntity<Void> response = controller.activatePot(potId);

        verify(service, times(1)).activatePot(potId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void getPotsForUser() {
        Long userId = 1L;
        List<Pot> pots = List.of(new Pot(), new Pot());

        when(service.findPotsByUserId(userId)).thenReturn(pots);

        ResponseEntity<List<Instruction>> response = controller.getPotsForUser(userId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
    }
}