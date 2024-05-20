package com.discipline.Dtos;

import lombok.Getter;
import lombok.Setter;
import java.util.Set;

@Getter
@Setter
public class CycleDTO {
    private Long id;
    private String cycle;
    private Set<FiliereDTO> filieres;
}

