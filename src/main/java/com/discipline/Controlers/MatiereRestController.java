package com.discipline.Controlers;

import java.util.*;

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

import com.discipline.Services.ServicesImplementations.*;
import com.discipline.entities.Cours;
import com.discipline.entities.Enseignant;
import com.discipline.entities.Matiere;
import com.discipline.entities.Niveau;

@RestController

@RequestMapping("/matieres")
public class MatiereRestController {
    @Autowired
    MatiereServicesImplementation matiereServicesImplementation;
    @Autowired
    NiveauServicesImplementation niveauServicesImplementation;
    @Autowired
    CoursServicesImplementation coursServicesImplementation;
    @Autowired
    MatiereServicesImplementation matiereServicesImplementation2;
    @Autowired
    EnseignantServicesImplementation enseignantServicesImplementation;

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
    
        // Mettre à jour les champs de la matière
        matiere.setMatiere(matiereDetails.getMatiere());
    
        // Mettre à jour les enseignants
        if (matiereDetails.getEnseignants() != null) {
            Set<Enseignant> enseignants = new HashSet<>();
            for (Enseignant enseignantDetails : matiereDetails.getEnseignants()) {
                Enseignant enseignant = enseignantServicesImplementation.findEnseignantById(enseignantDetails.getId());
                if (enseignant != null) {
                    enseignants.add(enseignant);
                }
            }
            matiere.setEnseignants(enseignants);
        }
    
        // Mettre à jour les niveaux
        if (matiereDetails.getNiveaux() != null) {
            Set<Niveau> niveaux = new HashSet<>();
            for (Niveau niveauDetails : matiereDetails.getNiveaux()) {
                Niveau niveau = niveauServicesImplementation.findNiveauById(niveauDetails.getId());
                if (niveau != null) {
                    niveaux.add(niveau);
                }
            }
            matiere.setNiveaux(niveaux);
        }
    
        // Mettre à jour les cours
        if (matiereDetails.getCours() != null) {
            Set<Cours> coursSet = new HashSet<>();
            for (Cours coursDetails : matiereDetails.getCours()) {
                Cours cours = coursServicesImplementation.findCoursById(coursDetails.getId());
                if (cours != null) {
                    // Mettre à jour l'enseignant du cours s'il est spécifié
                    if (coursDetails.getEnseignant() != null && coursDetails.getEnseignant().getId() != null) {
                        Enseignant enseignant = enseignantServicesImplementation.findEnseignantById(coursDetails.getEnseignant().getId());
                        if (enseignant != null) {
                            cours.setEnseignant(enseignant);
                        } else {
                            return new ResponseEntity<>("Enseignant not found with id: " + coursDetails.getEnseignant().getId(), HttpStatus.NOT_FOUND);
                        }
                    }
                    coursSet.add(cours);
                }
            }
            matiere.setCours(coursSet);
        }
    
        Matiere updatedMatiere = matiereServicesImplementation.saveMatiere(matiere);
    
        return new ResponseEntity<>(updatedMatiere, HttpStatus.OK);
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

    @GetMapping("matiere/{matiere}")
    public ResponseEntity<Object> findMatiereBymatiere(@PathVariable String matiere){
        Matiere matiere1 = matiereServicesImplementation.findMatiereByMatiere(matiere);
        if (matiere1 != null){
            return new ResponseEntity<>(matiere1, HttpStatus.FOUND);
        }
        return new ResponseEntity<>("Cette filiere n'existe pas", HttpStatus.NOT_FOUND);
    }

}
