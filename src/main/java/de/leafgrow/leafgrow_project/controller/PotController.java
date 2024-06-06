package de.leafgrow.leafgrow_project.controller;

import de.leafgrow.leafgrow_project.domain.entity.Instruction;
import de.leafgrow.leafgrow_project.domain.entity.Pot;
import de.leafgrow.leafgrow_project.domain.entity.User;
import de.leafgrow.leafgrow_project.repository.PotRepository;
import de.leafgrow.leafgrow_project.service.interfaces.PotService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/pots")
public class PotController {
    private PotService service;
    private PotRepository repository;

    public PotController(PotService service, PotRepository repository) {
        this.service = service;
        this.repository = repository;
    }

    @GetMapping("/{potId}/instruction")
    public ResponseEntity<Instruction> getInstructionForPot(@PathVariable Long potId) {
        Optional<Pot> potOptional = repository.findById(potId);
        if (potOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Pot pot = potOptional.get();

        return ResponseEntity.ok(pot.getInstruction());
    }

    @PostMapping("/{potId}/refresh")
    public ResponseEntity<Void> refreshPot(@PathVariable Long potId) {
        Optional<Pot> potOptional = repository.findById(potId);
        if (potOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Pot pot = potOptional.get();

        service.refreshPot(pot);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/create")
    public ResponseEntity<Void> createPotsForUser(@RequestBody User user) {
        service.createPotsForUser(user);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{potId}/activate")
    public ResponseEntity<Void> activatePot(@PathVariable Long potId) {
        service.activatePot(potId);
        return ResponseEntity.ok().build();
    }
}
