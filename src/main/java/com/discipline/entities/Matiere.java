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
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString @Builder
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Entity
@Table(name = "matiere")
public class Matiere {
    @Id @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    @Column(unique=true)
    private String matiere;

    // Définir la relation Many-to-Many avec Niveau
    @ManyToMany
    @JoinTable(name = "matiere_niveau",
            joinColumns = @JoinColumn(name = "matiere_id"),
            inverseJoinColumns = @JoinColumn(name = "niveau_id"))
    private Set<Niveau> niveaux ;

    @ManyToMany
    @JoinTable(name = "enseignant_matiere",
            joinColumns = @JoinColumn(name = "matiere_id"),
            inverseJoinColumns = @JoinColumn(name = "enseignant_id"))
    private Set<Enseignant> enseignants;

    @OneToMany(mappedBy = "matiere")
    private Set<Cours> cours ;
}
