package de.leafgrow.project.controller;

import de.leafgrow.project.domain.dto.PotDto;
import de.leafgrow.project.service.PlantCareService;
import de.leafgrow.project.service.PotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
// Обновление контроллера Pot для интеграции с PlantCareService
@RestController
@RequestMapping("/api/pots")
public class PotController {

    private final PotService potService;
    private final PlantCareService plantCareService;

    @Autowired
    public PotController(PotService potService, PlantCareService plantCareService) {
        this.potService = potService;
        this.plantCareService = plantCareService;
    }

    @GetMapping
    public List<PotDto> getAllPots() {
        return potService.getAllPots();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PotDto> getPotById(@PathVariable Long id) {
        PotDto potDto = potService.getPotById(id);
        return potDto != null ? ResponseEntity.ok(potDto) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public PotDto createPot(@RequestBody PotDto potDto) {
        return potService.createPot(potDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PotDto> updatePot(@PathVariable Long id, @RequestBody PotDto potDto) {
        PotDto updatedPot = potService.updatePot(id, potDto);
        return updatedPot != null ? ResponseEntity.ok(updatedPot) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePot(@PathVariable Long id) {
        potService.deletePot(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/care-instructions")
    public ResponseEntity<String> getCareInstructions(@PathVariable Long id) {
        PotDto potDto = potService.getPotById(id);
        if (potDto == null) {
            return ResponseEntity.notFound().build();
        }
        String careInstructions = plantCareService.getCareInstructions(potDto.getPlantName());
        return ResponseEntity.ok(careInstructions);
    }

    @GetMapping("/{id}/analyze")
    public ResponseEntity<String> analyzePlantData(@PathVariable Long id) {
        PotDto potDto = potService.getPotById(id);
        if (potDto == null) {
            return ResponseEntity.notFound().build();
        }
        String analysisResult = plantCareService.analyzePlantData(potDto.getPlantName());
        return ResponseEntity.ok(analysisResult);
    }
}
