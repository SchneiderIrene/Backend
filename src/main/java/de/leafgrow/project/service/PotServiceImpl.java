package de.leafgrow.project.service;


import de.leafgrow.project.domain.dto.PotDto;
import de.leafgrow.project.domain.entity.Pot;
import de.leafgrow.project.domain.entity.User;
import de.leafgrow.project.repository.PotRepository;
import de.leafgrow.project.service.interfaces.PotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PotServiceImpl implements PotService {


    @Autowired
    private PotRepository potRepository;


    public List<Pot> getAllPots() {
        return potRepository.findAll();
    }


    public Optional<Pot> getPotById(Long id) {
        return potRepository.findById(id);
    }


    public Pot savePot(Pot pot) {
        return potRepository.save(pot);
    }


    public void deletePot(Long id) {
        potRepository.deleteById(id);
    }


    public Pot updatePot(Long id, Pot potDetails) {
        Optional<Pot> potOptional = potRepository.findById(id);
        if (potOptional.isPresent()) {
            Pot pot = potOptional.get();
            pot.setName(potDetails.getName());
            pot.setDescription(potDetails.getDescription());
            pot.setCapacity(potDetails.getCapacity());
            return potRepository.save(pot);
        } else {
            throw new RuntimeException("Pot not found with id " + id);
        }
    }

    @Override
    public Pot addPot(PotDto potDto, User user) {
        return null;
    }

    @Override
    public List<Pot> getPots(User user) {
        return null;
    }

    @Override
    public void deletePot(Long potId, User user) {

    }

    @Override
    public List<Pot> getPotsByUserId(Long userId) {
        return null;
    }
 /*   @Autowired
    private PotRepository potRepository;

    @Override
    public Pot addPot(PotDto potDto, User user) {
        PotDto pot = new PotDto();
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
    }*/
}
