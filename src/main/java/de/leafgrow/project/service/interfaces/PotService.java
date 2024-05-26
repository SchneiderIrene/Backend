package de.leafgrow.project.service.interfaces;


import de.leafgrow.project.domain.dto.PotDto;
import de.leafgrow.project.domain.entity.Pot;
import de.leafgrow.project.domain.entity.User;

import java.util.List;

public interface PotService {
    Pot addPot(PotDto potDto, User user);
    List<Pot> getPots(User user);
    void deletePot(Long potId, User user);

    List<Pot> getPotsByUserId(Long userId);
}

