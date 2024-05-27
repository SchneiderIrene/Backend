package de.leafgrow.leafgrow_project.domain.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "instruction")
public class Instruction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "content")
    private String content;

    @Column(name = "is_important")
    private boolean isImportant;

    @Column(name = "day")
    private LocalDateTime day;

    @Column(name = "image")
    private String image;

    public Instruction(Long id, String content, boolean isImportant, LocalDateTime day, String image) {
        this.id = id;
        this.content = content;
        this.isImportant = isImportant;
        this.day = day;
        this.image = image;
    }

    public Instruction() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isImportant() {
        return isImportant;
    }

    public void setImportant(boolean important) {
        isImportant = important;
    }

    public LocalDateTime getDay() {
        return day;
    }

    public void setDay(LocalDateTime day) {
        this.day = day;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Instruction that = (Instruction) o;

        if (isImportant != that.isImportant) return false;
        if (!Objects.equals(id, that.id)) return false;
        if (!Objects.equals(content, that.content)) return false;
        if (!Objects.equals(day, that.day)) return false;
        return Objects.equals(image, that.image);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (content != null ? content.hashCode() : 0);
        result = 31 * result + (isImportant ? 1 : 0);
        result = 31 * result + (day != null ? day.hashCode() : 0);
        result = 31 * result + (image != null ? image.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return String.format("Instruction: ID - %d, content - %s, day - %s, image - %s, important - %s",
                id, content, day, image, isImportant ? "yes" : "no");
    }
}
