package de.leafgrow.project.domain.dto;

import java.util.Objects;

public class PotDto {
    private Long id;
    private String plantName;
    private boolean activated;

    public String getPlantName() {
        return plantName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setPlantName(String plantName) {
        this.plantName = plantName;
    }

    public boolean isActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PotDto potDto = (PotDto) o;
        return activated == potDto.activated && Objects.equals(id, potDto.id) && Objects.equals(plantName, potDto.plantName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, plantName, activated);
    }

    @Override
    public String toString() {
        return "PotDto{" +
                "plantName='" + plantName + '\'' +
                ", activated=" + activated +
                '}';
    }
}
