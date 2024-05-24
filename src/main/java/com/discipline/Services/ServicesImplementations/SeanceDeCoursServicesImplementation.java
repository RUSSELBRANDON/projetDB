package com.discipline.Services.ServicesImplementations;

import com.discipline.Services.SeanceDeCoursServices;
import com.discipline.entities.Matiere;
import com.discipline.entities.Salle;
import com.discipline.entities.SeanceDeCours;
import com.discipline.repositories.SeanceDeCoursRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class SeanceDeCoursServicesImplementation implements SeanceDeCoursServices {

    @Autowired
    SeanceDeCoursRepository seanceDeCoursRepository;

    @Autowired
    SalleServicesImplementation salleServicesImplementation;

    @Autowired
    MatiereServicesImplementation matiereServicesImplementation;

    @Override
    public SeanceDeCours ajouterSeanceDeCours(SeanceDeCours seanceDeCours) {
        return seanceDeCoursRepository.save(seanceDeCours);
    }

    @Override
    public List<SeanceDeCours> getSeancesDeCours(String salle, String matiere) {
        Salle salle1 = salleServicesImplementation.findByClasseName(salle);
        Matiere matiere1 = matiereServicesImplementation.findByMatiereName(matiere);
        LocalDate debut = LocalDate.now();
        LocalDate fin = debut.plusDays(7);
        return seanceDeCoursRepository.findBySalleAndMatiereAndJourBetween(salle1, matiere1, debut,fin);
    }

    @Override
    public void  supprimerSeanceDeCours(Long id) {
        seanceDeCoursRepository.deleteById(id);
    }

    @Override
    public void supprimerToutesLesSeances() {
        seanceDeCoursRepository.deleteAll();
    }

    @Override
    public Boolean ifExistsBySeanceDeCoursId(Long id) {
        List<SeanceDeCours> seanceDeCoursList = seanceDeCoursRepository.findAll();
        for (SeanceDeCours seanceDeCours : seanceDeCoursList){
            if (seanceDeCours.getId().equals(id)){
                return true;
            }
        }
        return false;
    }

    @Override
    public Optional<SeanceDeCours> rechercherById(Long id) {
        return seanceDeCoursRepository.findById(id);
    }

}
