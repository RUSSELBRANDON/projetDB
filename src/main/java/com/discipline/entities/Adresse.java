package com.discipline.entities;

import javax.validation.constraints.NotNull;

import com.discipline.enums.TypeAdresse;
import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.*;
@Getter @Setter @ToString @Builder @NoArgsConstructor @AllArgsConstructor
@Entity
@Table(name = "adresse")
public class Adresse {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String adresseValue;
    private TypeAdresse type_adresse;

    // Relation ManyToOne avec Enseignant
    @ManyToOne
    @JoinColumn(name = "enseignant_id")

    @NotNull
    @JsonBackReference
    private Enseignant enseignant;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAdresseValue() {
        return adresseValue;
    }

    public void setAdresseValue(String adresseValue) {
        this.adresseValue = adresseValue;
    }

    public TypeAdresse getType_adresse() {
        return type_adresse;
    }

    public void setType_adresse(TypeAdresse type_adresse) {
        this.type_adresse = type_adresse;
    }

    public Enseignant getEnseignant() {
        return enseignant;
    }

    public void setEnseignant(Enseignant enseignant) {
        this.enseignant = enseignant;
    }
}
