package com.discipline.entities;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

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
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")

@Entity
@Table(name = "niveau")
public class Niveau {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique=true)
    private String niveau;

    @ManyToMany
    @JoinTable(name = "filiere_niveau",
               joinColumns = @JoinColumn(name = "niveau_id"),
               inverseJoinColumns = @JoinColumn(name = "filiere_id"))
    private Set<Filiere> filieres;

    // Définir la relation Many-to-Many avec Matiere
    @ManyToMany(mappedBy = "niveaux")
    private Set<Matiere> matieres;
}

