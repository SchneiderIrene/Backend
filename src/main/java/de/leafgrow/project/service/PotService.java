package de.leafgrow.project.service;

import de.leafgrow.project.dto.PotDto;
import de.leafgrow.project.model.Pot;
import de.leafgrow.project.model.User;
import de.leafgrow.project.repository.PotRepository;
import de.leafgrow.project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PotService {

    @Autowired
    private PotRepository potRepository;

    @Autowired
    private UserRepository userRepository;

    public Pot addPot(PotDto potDto) {
        String username = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        User user = userRepository.findByEmail(username).orElseThrow(() -> new IllegalStateException("User not found"));
        if (user.getPots().size() >= 3) {
            throw new IllegalStateException("Cannot add more than 3 pots");
        }
        Pot pot = new Pot();
        pot.setPlantName(potDto.getPlantName());
        pot.setDescription(potDto.getDescription());
        pot.setUser(user);
        return potRepository.save(pot);
    }

    public List<Pot> getPots() {
        String username = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        User user = userRepository.findByEmail(username).orElseThrow(() -> new IllegalStateException("User not found"));
        return potRepository.findAllByUser(user);
    }

    public void deletePot(Long potId) {
        potRepository.deleteById(potId);
    }
}
