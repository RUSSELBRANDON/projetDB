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

import com.discipline.Services.ServicesImplementations.MatiereServicesImplementation;
import com.discipline.entities.Matiere;

@RestController

@RequestMapping("/matieres")
public class MatiereRestController {
    @Autowired
    MatiereServicesImplementation matiereServicesImplementation;

    @GetMapping

    public List<Matiere> listeMatiere() {
        return matiereServicesImplementation.findAllMatieres();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getMatiere(@PathVariable("id") Long id) {
        Matiere matiere = matiereServicesImplementation.findMatiereById(id);
        if (matiere != null) {
            return ResponseEntity.ok(matiere);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Matiere not found with id: " + id);
        }
    }

    @PostMapping
    public ResponseEntity<?> ajouterMatiere(@Valid @RequestBody Matiere matiere, BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<>("Erreur de validation", HttpStatus.BAD_REQUEST);
        }

        Matiere savedMatiere = matiereServicesImplementation.saveMatiere(matiere);

        return new ResponseEntity<>(savedMatiere, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> mettreAJourMatiere(@PathVariable Long id, @Valid @RequestBody Matiere matiereDetails, BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<>("Erreur de validation", HttpStatus.BAD_REQUEST);
        }

        Matiere matiere = matiereServicesImplementation.findMatiereById(id);

        if (matiere == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Matiere not found with id: " + id);
        }

        // Mettre à jour les champs de l'matiere
        matiere.setMatiere(matiereDetails.getMatiere());

        Matiere updatedAdresse = matiereServicesImplementation.saveMatiere(matiere);

        return new ResponseEntity<>(updatedAdresse, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> supprimerMatiere(@PathVariable Long id) {
        Matiere matiere = matiereServicesImplementation.findMatiereById(id);

        if (matiere == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Matiere not found with id: " + id);
        }

        matiereServicesImplementation.deleteMatiereById(matiere.getId());

        return new ResponseEntity<>("Matiere supprimé avec succès", HttpStatus.OK);
    }

}
