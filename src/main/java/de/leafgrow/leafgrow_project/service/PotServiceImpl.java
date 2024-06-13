package de.leafgrow.leafgrow_project.service;

import de.leafgrow.leafgrow_project.domain.entity.Instruction;
import de.leafgrow.leafgrow_project.domain.entity.Pot;
import de.leafgrow.leafgrow_project.domain.entity.User;
import de.leafgrow.leafgrow_project.repository.InstructionRepository;
import de.leafgrow.leafgrow_project.repository.PotRepository;
import de.leafgrow.leafgrow_project.service.interfaces.EmailService;
import de.leafgrow.leafgrow_project.service.interfaces.InstructionService;
import de.leafgrow.leafgrow_project.service.interfaces.PotService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
public class PotServiceImpl implements PotService {
    private static final int MAX_DAYS = 18;
    private PotRepository potRepository;
    private InstructionRepository instructionRepository;
    private InstructionService instructionService;
    private ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private EmailService emailService;

    public PotServiceImpl(PotRepository potRepository,
                          InstructionRepository instructionRepository,
                          InstructionService instructionService,
                          @Lazy EmailService emailService) {
        this.potRepository = potRepository;
        this.instructionRepository = instructionRepository;
        this.instructionService = instructionService;
        this.emailService = emailService;
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
    public Pot activatePot(Long potId) {
        Pot pot = potRepository.findById(potId).orElseThrow(() -> new RuntimeException("Pot not found"));
        pot.setActive(true);
        pot.setInstruction(instructionRepository.findByDay(1));
        potRepository.save(pot);

        scheduler.scheduleAtFixedRate(() -> updateInstruction(pot), 24, 24, TimeUnit.HOURS);
        //scheduler.scheduleAtFixedRate(() -> updateInstruction(pot), 24, 24,TimeUnit.SECONDS);

        return potRepository.save(pot);

    }

    private void updateInstruction(Pot pot) {
        if (pot.isActive()) {
            int currentDay = pot.getInstruction().getDay();
            Instruction nextInstruction = instructionService.getInstructionForDay(currentDay + 1);

            if (nextInstruction != null) {
                pot.setInstruction(nextInstruction);
                potRepository.save(pot);

                // Отправка письма, если инструкция важна
                if (nextInstruction.isImportant()) {
                    emailService.sendImportantEmail(pot.getUser());
                }
            } else {
                // Если инструкции на следующий день нет, деактивировать горшок или обнулить его
                pot.setActive(false);
                potRepository.save(pot);
            }
        }
    }

    public void skipDay(Pot pot) {
        int currentDay = pot.getInstruction().getDay();
        int nextDay = (currentDay % MAX_DAYS) + 1; // Предполагая, что MAX_DAYS — это длина цикла.
        Instruction nextInstruction = instructionRepository.findByDay(nextDay);
        pot.setInstruction(nextInstruction);
        potRepository.save(pot);
    }

    public List<Pot> findPotsByUserId(Long userId) {
        List<Pot> pots = potRepository.findByUserId(userId);
        pots.sort(Comparator.comparingLong(Pot::getId));
        return pots;
    }
}
