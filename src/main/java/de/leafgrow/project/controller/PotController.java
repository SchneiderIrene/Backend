package de.leafgrow.project.controller;

import de.leafgrow.project.dto.PotDto;
import de.leafgrow.project.service.PotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pots")
public class PotController {

    @Autowired
    private PotService potService;

    @PostMapping("/add")
    public ResponseEntity<?> addPot(@RequestBody PotDto potDto) {
        return ResponseEntity.ok(potService.addPot(potDto));
    }

    @GetMapping
    public ResponseEntity<?> getPots() {
        return ResponseEntity.ok(potService.getPots());
    }

    @DeleteMapping("/{potId}")
    public ResponseEntity<?> deletePot(@PathVariable Long potId) {
        potService.deletePot(potId);
        return ResponseEntity.ok().build();
    }
}
