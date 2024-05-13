package com.discipline.entities;

import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString @Builder

@Entity
@Table(name = "salle")
public class Salle {
    private Long id;
    private String salle;

     @OneToMany(mappedBy = "salle")
    private Set<Cours> cours ;
}
