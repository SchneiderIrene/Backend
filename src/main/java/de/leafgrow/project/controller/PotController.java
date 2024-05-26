package de.leafgrow.project.controller;

import de.leafgrow.project.domain.dto.PotDto;
import de.leafgrow.project.domain.entity.Pot;
import de.leafgrow.project.domain.entity.User;
import de.leafgrow.project.service.interfaces.PotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PotController {
    @Autowired
    private PotService potService;

    @GetMapping("/pots")
    public ResponseEntity<List<Pot>> getPotsByUserId(@RequestParam Long userId) {
        return ResponseEntity.ok(potService.getPotsByUserId(userId));
    }

    @PostMapping("/pots")
    public ResponseEntity<Pot> addPot(@AuthenticationPrincipal User user, @RequestBody PotDto potDto) {
        return ResponseEntity.ok(potService.addPot(potDto, user));
    }

    @DeleteMapping("/pots/{id}")
    public ResponseEntity<Void> deletePot(@PathVariable Long id, @AuthenticationPrincipal User user) {
        potService.deletePot(id, user);
        return ResponseEntity.noContent().build();
    }

}
