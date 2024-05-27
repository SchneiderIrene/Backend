package de.leafgrow.project.domain.entity;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "pot")
public class Pot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "plant_name", nullable = false)
    private String plantName;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private boolean activated;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPlantName() {
        return plantName;
    }

    public void setPlantName(String plantName) {
        this.plantName = plantName;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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
        Pot pot = (Pot) o;
        return activated == pot.activated && Objects.equals(id, pot.id) && Objects.equals(plantName, pot.plantName) && Objects.equals(user, pot.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, plantName, user, activated);
    }

    @Override
    public String toString() {
        return "Pot{" +
                "id=" + id +
                ", plantName='" + plantName + '\'' +
                ", user=" + user +
                ", activated=" + activated +
                '}';
    }
}
