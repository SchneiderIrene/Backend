package de.leafgrow.leafgrow_project.domain.dto;

import de.leafgrow.leafgrow_project.domain.entity.Instruction;

public class PotDto {
    private Long potId;
    private boolean isActive;
    private Instruction instruction;

    // Конструктор
    public PotDto(Long potId, boolean isActive, Instruction instruction) {
        this.potId = potId;
        this.isActive = isActive;
        this.instruction = instruction;
    }

    // Геттеры и сеттеры
    public Long getPotId() {
        return potId;
    }

    public void setPotId(Long potId) {
        this.potId = potId;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public Instruction getInstruction() {
        return instruction;
    }

    public void setInstruction(Instruction instruction) {
        this.instruction = instruction;
    }
}

