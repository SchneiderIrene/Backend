package de.leafgrow.project.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Pot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String plantName;
    private String description;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private boolean active;
}
