package com.discipline.entities;

import java.util.Set;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.List;
import lombok.*;


@Getter @Setter @NoArgsConstructor @Builder @AllArgsConstructor @ToString
@Entity
@Table(name = "enseignant")
public class Enseignant {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY )
    private Long id;
    private String matricule;
    private String nom;
    private String prenmon;

    // Relation OneToMany avec Adresse
    @OneToMany(mappedBy = "enseignant")
    private List<Adresse> adresses;

    @ManyToMany(mappedBy = "enseignants")
    private Set<Matiere> matieres;

    @OneToMany(mappedBy = "enseignant")
    private Set<Cours> cours ;
}
