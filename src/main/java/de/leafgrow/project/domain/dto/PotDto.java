package de.leafgrow.project.domain.dto;


import de.leafgrow.project.domain.entity.User;

import java.util.Objects;

public class PotDto {
    private String plantName;
    private String description;

    private User user;

    public String getPlantName() {
        return plantName;
    }

    public void setPlantName(String plantName) {
        this.plantName = plantName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PotDto potDto = (PotDto) o;
        return Objects.equals(plantName, potDto.plantName) && Objects.equals(description, potDto.description) && Objects.equals(user, potDto.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(plantName, description, user);
    }

    @Override
    public String toString() {
        return "PotDto{" +
                "plantName='" + plantName + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}

