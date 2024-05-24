package com.discipline.Services;

import com.discipline.entities.SeanceDeCours;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface SeanceDeCoursServices {

    public SeanceDeCours ajouterSeanceDeCours(SeanceDeCours seanceDeCours);

    public List<SeanceDeCours> getSeancesDeCours(String salle, String matiere);

    public void supprimerSeanceDeCours(Long id);

    public void supprimerToutesLesSeances();

    public Boolean ifExistsBySeanceDeCoursId(Long id);

    public Optional<SeanceDeCours> rechercherById(Long id);

}
