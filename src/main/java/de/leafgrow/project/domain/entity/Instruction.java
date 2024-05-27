package de.leafgrow.project.domain.entity;


import jakarta.persistence.*;

@Entity
@Table(name = "instruction")
public class Instruction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String text;
}

