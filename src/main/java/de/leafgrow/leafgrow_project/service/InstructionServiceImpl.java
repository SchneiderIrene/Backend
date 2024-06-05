package de.leafgrow.leafgrow_project.service;

import de.leafgrow.leafgrow_project.domain.entity.Instruction;
import de.leafgrow.leafgrow_project.repository.InstructionRepository;
import de.leafgrow.leafgrow_project.service.interfaces.InstructionService;
import org.springframework.stereotype.Service;

@Service
public class InstructionServiceImpl implements InstructionService {
    private InstructionRepository repository;

    public InstructionServiceImpl(InstructionRepository repository) {
        this.repository = repository;
    }

    @Override
    public Instruction getInstructionForDay(int day) {
        return repository.findByDay(day);
    }

}
