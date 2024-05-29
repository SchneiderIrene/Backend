package de.leafgrow.leafgrow_project.domain.entity;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "pot")
public class Pot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "instruction_id")
    private Instruction instruction;

    @Column(name = "is_active")
    private boolean isActive;

    public Pot() {
    }

    public Pot(Long id, User user, Instruction instruction, boolean isActive) {
        this.id = id;
        this.user = user;
        this.instruction = instruction;
        this.isActive = isActive;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Instruction getInstruction() {
        return instruction;
    }

    public void setInstruction(Instruction instruction) {
        this.instruction = instruction;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Pot pot = (Pot) o;

        if (isActive != pot.isActive) return false;
        if (!Objects.equals(id, pot.id)) return false;
        if (!Objects.equals(user, pot.user)) return false;
        return Objects.equals(instruction, pot.instruction);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (user != null ? user.hashCode() : 0);
        result = 31 * result + (instruction != null ? instruction.hashCode() : 0);
        result = 31 * result + (isActive ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return String.format("Pot: ID - %d, user - %s, instruction - %s, active - %s",
                id, user, instruction, isActive ? "yes" : "no");
    }
}
