package com.discipline.entities;

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
    private String adresse;
    private String type_adresse;

    // Relation ManyToOne avec Enseignant
    @ManyToOne
    @JoinColumn(name = "enseignant_id")
    private Enseignant enseignant;
}
