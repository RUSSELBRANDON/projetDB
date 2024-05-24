package com.discipline.Services.ServicesImplementations;

import com.discipline.Services.CoursServices;
import com.discipline.entities.Cours;
import com.discipline.repositories.CoursRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class CoursServicesImplementation implements CoursServices {
    @Autowired
    CoursRepository coursRepository;
    @Override
    public Cours saveCours(Cours cours) {
        return coursRepository.save(cours);
    }

    @Override
    public Cours updateCours(Cours cours) {
        return coursRepository.save(cours);
    }

    @Override
    public Cours findCoursById(Long id) {
        return coursRepository.findById(id).get();
    }

    @Override
    public List<Cours> findAllCours() {
        return coursRepository.findAll();
    }

    @Override
    public void deleteCoursById(Long id) {
        coursRepository.deleteById(id);
    }

    @Override
    public void deleteAllCours() {
        coursRepository.deleteAll();
    }

    @Override
    public Cours findCoursByMatiere(String matiere) {
        List<Cours> coursList = coursRepository.findAll();
        for (Cours cours : coursList) {
            // Comparaison du nom de la matière du cours avec la chaîne 'matiere' passée en argument
            if (cours.getMatiere().getMatiere().equals(matiere)) {
                return cours;
            }
        }
        return null;
    }
    

}
