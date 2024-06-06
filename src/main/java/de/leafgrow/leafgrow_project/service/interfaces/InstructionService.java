package de.leafgrow.leafgrow_project.service.interfaces;

import de.leafgrow.leafgrow_project.domain.entity.Instruction;

public interface InstructionService {
    Instruction getInstructionForDay(int day);

}
