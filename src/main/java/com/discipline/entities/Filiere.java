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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getAnnee_scolaire() {
        return annee_scolaire;
    }

    public void setAnnee_scolaire(String annee_scolaire) {
        this.annee_scolaire = annee_scolaire;
    }

    public Long getNbre_place() {
        return nbre_place;
    }

    public void setNbre_place(Long nbre_place) {
        this.nbre_place = nbre_place;
    }

    public Set<Cycle> getCycles() {
        return cycles;
    }

    public void setCycles(Set<Cycle> cycles) {
        this.cycles = cycles;
    }

    public Set<Niveau> getNiveaux() {
        return niveaux;
    }

    public void setNiveaux(Set<Niveau> niveaux) {
        this.niveaux = niveaux;
    }
}
