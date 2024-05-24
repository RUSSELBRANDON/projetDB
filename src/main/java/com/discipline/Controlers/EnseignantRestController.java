package com.discipline.Controlers;

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

import com.discipline.Services.ServicesImplementations.EnseignantServicesImplementation;
import com.discipline.Services.ServicesImplementations.MatiereServicesImplementation;

import com.discipline.entities.Enseignant;
import com.discipline.entities.Matiere;
import java.util.*;
import javax.validation.Valid;
@RestController

@RequestMapping("/enseignants")
public class EnseignantRestController {
    @Autowired
    EnseignantServicesImplementation enseignantServicesImplementation;
    @Autowired
    MatiereServicesImplementation matiereServicesImplementation;
    @GetMapping

    public List<Enseignant> listeEnseignant() {
        return enseignantServicesImplementation.findAllEnseignants();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getEnseignant(@PathVariable("id") Long id) {
        Enseignant enseignant = enseignantServicesImplementation.findEnseignantById(id);
        if (enseignant != null) {
            return ResponseEntity.ok(enseignant);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Enseignant not found with id: " + id);
        }
    }

    @PostMapping
    public ResponseEntity<?> ajouterEnseignant(@Valid @RequestBody Enseignant enseignant, BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<>("Erreur de validation", HttpStatus.BAD_REQUEST);
        }

        // Handle associations

        if (enseignant.getMatieres() != null) {
            Set<Matiere> matieres = new HashSet<>();
            for (Matiere matiere : enseignant.getMatieres()) {
                Matiere existingMatiere = matiereServicesImplementation.findMatiereById(matiere.getId());
                if (existingMatiere != null) {
                    matieres.add(existingMatiere);
                }
            }
            enseignant.setMatieres(matieres);
        }

        Enseignant savedEnseignant = enseignantServicesImplementation.saveEnseignant(enseignant);

        return new ResponseEntity<>(savedEnseignant, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> mettreAJourEnseignant(@PathVariable Long id, @Valid @RequestBody Enseignant enseignantDetails, BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<>("Erreur de validation", HttpStatus.BAD_REQUEST);
        }

        Enseignant enseignant = enseignantServicesImplementation.findEnseignantById(id);

        if (enseignant == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Enseignant not found with id: " + id);
        }

        // Mettre à jour les champs de l'Enseignant
        enseignant.setMatricule(enseignantDetails.getMatricule());
        enseignant.setNom(enseignantDetails.getNom());
        enseignant.setPrenom(enseignantDetails.getPrenom());

        // Handle associations

        if (enseignantDetails.getMatieres() != null) {
            Set<Matiere> matieres = new HashSet<>();
            for (Matiere matiere : enseignantDetails.getMatieres()) {
                Matiere existingMatiere = matiereServicesImplementation.findMatiereById(matiere.getId());
                if (existingMatiere != null) {
                    matieres.add(existingMatiere);
                }
            }
            enseignant.setMatieres(matieres);
        }

        Enseignant updatedEnseignant = enseignantServicesImplementation.saveEnseignant(enseignant);

        return new ResponseEntity<>(updatedEnseignant, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> supprimerEnseignant(@PathVariable Long id) {
        Enseignant Enseignant = enseignantServicesImplementation.findEnseignantById(id);

        if (Enseignant == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Enseignant not found with id: " + id);
        }

        enseignantServicesImplementation.deleteEnseignantById(Enseignant.getId());

        return new ResponseEntity<>("Enseignant supprimé avec succès", HttpStatus.OK);
    }


    @GetMapping("/{matricule}")
    public ResponseEntity<Object> findEnseignantByMatricule(@PathVariable String matriculeEnseignant){
        Enseignant enseignant = enseignantServicesImplementation.findEnseignantByMatricule(matriculeEnseignant);
        if (enseignant != null){
            return new ResponseEntity<>(enseignant, HttpStatus.FOUND);
        }
        return new ResponseEntity<>("Cet enseignant n'existe pas", HttpStatus.NOT_FOUND);
    }
    
}
