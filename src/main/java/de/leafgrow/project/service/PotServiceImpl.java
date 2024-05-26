package de.leafgrow.project.service;


import de.leafgrow.project.domain.dto.PotDto;
import de.leafgrow.project.domain.entity.Pot;
import de.leafgrow.project.domain.entity.User;
import de.leafgrow.project.repository.PotRepository;
import de.leafgrow.project.service.interfaces.PotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PotServiceImpl implements PotService {

    @Autowired
    private PotRepository potRepository;

    @Override
    public Pot addPot(PotDto potDto, User user) {
        Pot pot = new Pot();
        pot.setPlantName(potDto.getPlantName());
        pot.setDescription(potDto.getDescription());
        pot.setUser(user);

        return potRepository.save(pot);
    }

    @Override
    public List<Pot> getPots(User user) {
        return potRepository.findByUserId(user.getId());
    }

    @Override
    public void deletePot(Long potId, User user) {
        Pot pot = potRepository.findById(potId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid pot Id:" + potId));
        if (pot.getUser().equals(user)) {
            potRepository.delete(pot);
        } else {
            throw new SecurityException("User is not authorized to delete this pot");
        }
    }

    @Override
    public List<Pot> getPotsByUserId(Long userId) {
        return potRepository.findByUserId(userId);
    }
}
