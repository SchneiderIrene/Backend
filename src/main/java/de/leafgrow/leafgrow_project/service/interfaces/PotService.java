package de.leafgrow.leafgrow_project.service.interfaces;

import de.leafgrow.leafgrow_project.domain.entity.Instruction;
import de.leafgrow.leafgrow_project.domain.entity.Pot;
import de.leafgrow.leafgrow_project.domain.entity.User;

import java.util.List;

public interface PotService {
    void refreshPot(Pot pot);

    void createPotsForUser(User user);

    Pot activatePot(Long potId);

    void skipDay(Pot pot);

    List<Pot> findPotsByUserId(Long userId);

    Pot createPotForAdmin(User user);
}
