package com.discipline.entities;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.List;
import lombok.*;


@Getter @Setter @NoArgsConstructor @Builder @AllArgsConstructor @ToString
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Entity
@Table(name = "enseignant")
public class Enseignant {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY )
    private Long id;

    @Column(unique = true)
    private String matricule;
    private String nom;
    private String prenom;

    // Relation OneToMany avec Adresse
    @OneToMany(mappedBy = "enseignant")
    @JsonManagedReference
    private List<Adresse> adresses;

    @ManyToMany(mappedBy = "enseignants")
    private Set<Matiere> matieres;

    @OneToMany(mappedBy = "enseignant")
   // @JsonManagedReference
    private Set<Cours> cours ;
}
