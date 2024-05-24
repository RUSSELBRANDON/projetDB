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
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.*;

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

    public Matiere(Long id, String matiere, Set<Niveau> niveaux, Set<Enseignant> enseignants, Set<Cours> cours) {
        this.id = id;
        this.matiere = matiere;
        this.niveaux = niveaux;
        this.enseignants = enseignants;
        this.cours = cours;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMatiere() {
        return matiere;
    }

    public void setMatiere(String matiere) {
        this.matiere = matiere;
    }

    public Set<Niveau> getNiveaux() {
        return niveaux;
    }

    public void setNiveaux(Set<Niveau> niveaux) {
        this.niveaux = niveaux;
    }

    public Set<Enseignant> getEnseignants() {
        return enseignants;
    }

    public void setEnseignants(Set<Enseignant> enseignants) {
        this.enseignants = enseignants;
    }

    public Set<Cours> getCours() {
        return cours;
    }

    public void setCours(Set<Cours> cours) {
        this.cours = cours;
    }
}
