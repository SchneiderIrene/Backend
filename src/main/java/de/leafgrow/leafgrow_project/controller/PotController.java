package de.leafgrow.leafgrow_project.controller;

import de.leafgrow.leafgrow_project.domain.dto.PotDto;
import de.leafgrow.leafgrow_project.domain.entity.Instruction;
import de.leafgrow.leafgrow_project.domain.entity.Pot;
import de.leafgrow.leafgrow_project.domain.entity.User;
import de.leafgrow.leafgrow_project.repository.PotRepository;
import de.leafgrow.leafgrow_project.service.interfaces.PotService;
import de.leafgrow.leafgrow_project.service.interfaces.UserService;
import org.springframework.http.HttpStatus;
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

    @GetMapping("/{id}/instruction")
    public ResponseEntity<Instruction> getInstructionForPot(@PathVariable Long id) {
        Optional<Pot> potOptional = repository.findById(id);
        if (potOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Pot pot = potOptional.get();

        return ResponseEntity.ok(pot.getInstruction());
    }

    @PostMapping("/{id}/refresh")
    public ResponseEntity<Pot> refreshPot(@PathVariable Long id) {
        Optional<Pot> potOptional = repository.findById(id);
        if (potOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Pot pot = potOptional.get();

        service.refreshPot(pot);
        return ResponseEntity.ok(pot);
    }

    @PostMapping("/create-pot-admin")
    public ResponseEntity<Pot> createPotForAdmin(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        User user = userService.loadUserByEmail(email);
        Pot pot = service.createPotForAdmin(user);
        repository.save(pot);
        return ResponseEntity.ok(pot);
    }

    @PostMapping("/create")
    public ResponseEntity<Void> createPotsForUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        User user = userService.loadUserByEmail(email);
        service.createPotsForUser(user);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/activate")
    public ResponseEntity<Pot> activatePot(@PathVariable Long id) {
        Pot pot = service.activatePot(id);
        return ResponseEntity.ok(pot);
    }

    @PostMapping("/{id}/skip-day")
    //@PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> skipDay(@PathVariable Long id) {
        Optional<Pot> potOptional = repository.findById(id);
        if (potOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Pot pot = potOptional.get();
        service.skipDay(pot);
        return ResponseEntity.ok().build();
    }


    @GetMapping("/my")
    public ResponseEntity<List<PotDto>> getPotsForUser() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userContent = authentication.getName();
            User user = userService.loadUserByEmail(userContent);

            List<Pot> pots = service.findPotsByUserId(user.getId());
            List<PotDto> potsDto = pots.stream()
                    .map(pot -> new PotDto(pot.getId(), pot.isActive(), pot.getInstruction()))
                    .collect(Collectors.toList());
            return ResponseEntity.ok(potsDto);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

    }
}
