package de.leafgrow.leafgrow_project.service;

import de.leafgrow.leafgrow_project.domain.entity.Instruction;
import de.leafgrow.leafgrow_project.domain.entity.Pot;
import de.leafgrow.leafgrow_project.domain.entity.User;
import de.leafgrow.leafgrow_project.repository.InstructionRepository;
import de.leafgrow.leafgrow_project.repository.PotRepository;
import de.leafgrow.leafgrow_project.service.interfaces.InstructionService;
import de.leafgrow.leafgrow_project.service.interfaces.PotService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PotServiceImpl implements PotService {
    private PotRepository potRepository;
    private InstructionRepository instructionRepository;

    public PotServiceImpl(PotRepository potRepository, InstructionRepository instructionRepository) {
        this.potRepository = potRepository;
        this.instructionRepository = instructionRepository;
    }

    @Override
    @Transactional
    public void refreshPot(Pot pot) {
        pot.setActive(false);
        pot.setInstruction(instructionRepository.findByDay(1));
        potRepository.save(pot);
    }

    @Override
    @Transactional
    public void createPotsForUser(User user) {
        for (int i = 0; i < 3; i++) {
            Pot pot = new Pot();
            pot.setUser(user);
            pot.setInstruction(instructionRepository.findByDay(1));
            potRepository.save(pot);
        }
    }

    @Override
    @Transactional
    public void activatePot(Long potId) {
        Pot pot = potRepository.findById(potId).orElseThrow(() -> new RuntimeException("Pot not found"));
        pot.setActive(true);
        pot.setInstruction(instructionRepository.findByDay(1));
        potRepository.save(pot);
    }
}
