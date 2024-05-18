package com.discipline.entities;


import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString @Builder

@Entity
@Table(name = "filiere")
public class Filiere {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique=true) 
    private String nom;
    private String annee_scolaire;
    private Long nbre_place;

    // Relation ManyToMany avec Cycle
    @ManyToMany(mappedBy = "filieres")
    private Set<Cycle> cycles;

    @ManyToMany(mappedBy = "filieres")
    private Set<Niveau> niveaux;
}
