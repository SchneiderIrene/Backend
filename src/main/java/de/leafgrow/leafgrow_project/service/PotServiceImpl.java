package de.leafgrow.leafgrow_project.service;

import de.leafgrow.leafgrow_project.domain.entity.Instruction;
import de.leafgrow.leafgrow_project.domain.entity.Pot;
import de.leafgrow.leafgrow_project.domain.entity.User;
import de.leafgrow.leafgrow_project.repository.InstructionRepository;
import de.leafgrow.leafgrow_project.repository.PotRepository;
import de.leafgrow.leafgrow_project.service.interfaces.EmailService;
import de.leafgrow.leafgrow_project.service.interfaces.InstructionService;
import de.leafgrow.leafgrow_project.service.interfaces.PotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PotServiceImpl implements PotService {
    private static final int MAX_DAYS = 30;
    private PotRepository potRepository;
    private InstructionRepository instructionRepository;
    private EmailService emailService;


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

    public void resetPot(Pot pot) {
        pot.setActive(false);
        pot.setInstruction(instructionRepository.findByDay(1)); // Предполагая, что день 1 — это начало
        potRepository.save(pot);
    }

    public void skipDay(Pot pot) {
        int currentDay = pot.getInstruction().getDay();
        int nextDay = (currentDay % MAX_DAYS) + 1; // Предполагая, что MAX_DAYS — это длина цикла.
        Instruction nextInstruction = instructionRepository.findByDay(nextDay);
        pot.setInstruction(nextInstruction);
        potRepository.save(pot);
    }

    public List<Pot> findPotsByUserId(Long userId) {
        return potRepository.findByUserId(userId);
    }

    @Scheduled(cron = "0 0 0 * * ?") // каждый день в полночь
    public void checkAndSendImportantDayEmails() {
        List<Pot> activePots = potRepository.findAllByIsActive(true);
        for (Pot pot : activePots) {
            if (pot.getInstruction().isImportant()) {
                emailService.sendImportantDayEmail(pot.getUser().getEmail());
            }
        }
    }
}
