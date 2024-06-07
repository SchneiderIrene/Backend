package de.leafgrow.leafgrow_project.controller;

import de.leafgrow.leafgrow_project.domain.entity.Instruction;
import de.leafgrow.leafgrow_project.domain.entity.Pot;
import de.leafgrow.leafgrow_project.domain.entity.User;
import de.leafgrow.leafgrow_project.repository.PotRepository;
import de.leafgrow.leafgrow_project.service.interfaces.PotService;
import de.leafgrow.leafgrow_project.service.interfaces.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/pots")
public class PotController {
    private PotService service;
    private PotRepository repository;
    private UserService userService;

    public PotController(PotService service, PotRepository repository, UserService userService) {
        this.service = service;
        this.repository = repository;
        this.userService = userService;
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
    public ResponseEntity<Void> createPotsForUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        User user = userService.loadUserByEmail(email);
        service.createPotsForUser(user);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{potId}/activate")
    public ResponseEntity<Void> activatePot(@PathVariable Long potId) {
        service.activatePot(potId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{potId}/skip-day")
    //@PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> skipDay(@PathVariable Long potId) {
        Optional<Pot> potOptional = repository.findById(potId);
        if (potOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Pot pot = potOptional.get();
        service.skipDay(pot);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/user/{userId}/pots")
    public ResponseEntity<List<Instruction>> getPotsForUser(@PathVariable Long userId) {
        List<Pot> pots = service.findPotsByUserId(userId);
        List<Instruction> instructions = pots.stream()
                .map(Pot::getInstruction)
                .collect(Collectors.toList());
        return ResponseEntity.ok(instructions);
    }
}
