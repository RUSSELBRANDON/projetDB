package com.discipline.Controlers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.discipline.Services.ServicesImplementations.CoursServicesImplementation;
import com.discipline.entities.Cours;

@RestController

@RequestMapping("/cours")
public class CoursRestController {
    @Autowired
    CoursServicesImplementation coursServicesImplementation;

    @GetMapping

    public List<Cours> listeCours() {
        return coursServicesImplementation.findAllCours();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCours(@PathVariable("id") Long id) {
        Cours cours = coursServicesImplementation.findCoursById(id);
        if (cours != null) {
            return ResponseEntity.ok(cours);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Cours not found with id: " + id);
        }
    }

    @PostMapping
    public ResponseEntity<?> ajouterCours(@Valid @RequestBody Cours cours, BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<>("Erreur de validation", HttpStatus.BAD_REQUEST);
        }

        Cours savedCours = coursServicesImplementation.saveCours(cours);

        return new ResponseEntity<>(savedCours, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> mettreAJourCours(@PathVariable Long id, @Valid @RequestBody Cours coursDetails, BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<>("Erreur de validation", HttpStatus.BAD_REQUEST);
        }

        Cours cours = coursServicesImplementation.findCoursById(id);

        if (cours == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Cours not found with id: " + id);
        }

        // Mettre à jour les champs du cours
        cours.setTitre(coursDetails.getTitre());
        cours.setJour(coursDetails.getJour());


        Cours updatedSalle = coursServicesImplementation.saveCours(cours);

        return new ResponseEntity<>(updatedSalle, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> supprimerCours(@PathVariable Long id) {
        Cours cours = coursServicesImplementation.findCoursById(id);

        if (cours == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Cours not found with id: " + id);
        }

        coursServicesImplementation.deleteCoursById(cours.getId());
        return new ResponseEntity<>("Cours supprimé avec succès", HttpStatus.OK);
    }

}
