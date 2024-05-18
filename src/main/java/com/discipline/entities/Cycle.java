package com.discipline.entities;

import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString @Builder
@Entity
@Table(name="cycle")
public class Cycle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique=true)
    private String cycle;

    // Relation ManyToMany avec Filiere
    @ManyToMany
    @JoinTable(name = "cycle_filiere",
               joinColumns = @JoinColumn(name = "cycle_id"),
               inverseJoinColumns = @JoinColumn(name = "filiere_id"))
    private Set<Filiere> filieres;
}
