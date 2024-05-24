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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNiveau() {
        return niveau;
    }

    public void setNiveau(String niveau) {
        this.niveau = niveau;
    }

    public Set<Filiere> getFilieres() {
        return filieres;
    }

    public void setFilieres(Set<Filiere> filieres) {
        this.filieres = filieres;
    }

    public Set<Matiere> getMatieres() {
        return matieres;
    }

    public void setMatieres(Set<Matiere> matieres) {
        this.matieres = matieres;
    }
}

