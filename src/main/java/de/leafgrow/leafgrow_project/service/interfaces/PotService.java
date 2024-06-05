package de.leafgrow.leafgrow_project.service.interfaces;

import de.leafgrow.leafgrow_project.domain.entity.Instruction;
import de.leafgrow.leafgrow_project.domain.entity.Pot;
import de.leafgrow.leafgrow_project.domain.entity.User;

public interface PotService {
    void refreshPot(Pot pot);
    void createPotsForUser(User user);
    void activatePot(Long potId);

}
