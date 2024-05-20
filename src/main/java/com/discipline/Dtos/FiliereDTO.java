package com.discipline.Dtos;

import java.util.Set;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FiliereDTO {
    private Long id;
    private String nom;
    private String annee_scolaire;
    private Long nbre_place;
    private Set<CycleDTO> cycles;
}

