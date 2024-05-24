package com.discipline.repositories;

import com.discipline.entities.Matiere;
import com.discipline.entities.Salle;
import com.discipline.entities.SeanceDeCours;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface SeanceDeCoursRepository extends JpaRepository<SeanceDeCours,Long> {

    List<SeanceDeCours> findBySalleAndMatiereAndJourBetween(Salle salle, Matiere matiere, LocalDate debut, LocalDate fin);

}
