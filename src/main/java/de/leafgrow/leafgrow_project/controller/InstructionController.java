package de.leafgrow.leafgrow_project.controller;

import de.leafgrow.leafgrow_project.domain.entity.Instruction;
import de.leafgrow.leafgrow_project.service.interfaces.InstructionService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/instructions")
public class InstructionController {
    private InstructionService service;

    public InstructionController(InstructionService service) {
        this.service = service;
    }

    @GetMapping("/{day}")
    @Operation(
            summary = "get instruction by day",
            description = "Getting instruction by day of the cycle"
    )
    public ResponseEntity<Instruction> getInstructionByDay(@PathVariable int day) {
        return ResponseEntity.ok(service.getInstructionForDay(day));
    }
}
