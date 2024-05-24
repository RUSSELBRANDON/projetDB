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

import com.discipline.Services.ServicesImplementations.FiliereServicesImplementation;
import com.discipline.Services.ServicesImplementations.NiveauServicesImplementation;
import com.discipline.Services.ServicesImplementations.MatiereServicesImplementation;

import com.discipline.entities.Filiere;
import com.discipline.entities.Matiere;
import com.discipline.entities.Niveau;


@RestController
@RequestMapping("/niveaux")
public class NiveauRestController {
    @Autowired
    NiveauServicesImplementation niveauServicesImplementation;

    @Autowired
    FiliereServicesImplementation filiereServicesImplementation;

    @Autowired
    MatiereServicesImplementation matiereServicesImplementation;

    @GetMapping
    public List<Niveau> listeNiveau() {
        return niveauServicesImplementation.findAllNiveaux();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getNiveau(@PathVariable("id") Long id) {
        Niveau niveau = niveauServicesImplementation.findNiveauById(id);
        if (niveau != null) {
            return ResponseEntity.ok(niveau);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Niveau not found with id: " + id);
        }
    }

    @PostMapping
    public ResponseEntity<?> ajouterNiveau(@Valid @RequestBody Niveau niveau, BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<>("Erreur de validation", HttpStatus.BAD_REQUEST);
        }

        // Handle associations
        if (niveau.getFilieres() != null) {
            Set<Filiere> filieres = new HashSet<>();
            for (Filiere filiere : niveau.getFilieres()) {
                Filiere existingFiliere = filiereServicesImplementation.findFiliereById(filiere.getId());
                if (existingFiliere != null) {
                    filieres.add(existingFiliere);
                }
            }
            niveau.setFilieres(filieres);
        }

        if (niveau.getMatieres() != null) {
            Set<Matiere> matieres = new HashSet<>();
            for (Matiere matiere : niveau.getMatieres()) {
                Matiere existingMatiere = matiereServicesImplementation.findMatiereById(matiere.getId());
                if (existingMatiere != null) {
                    matieres.add(existingMatiere);
                }
            }
            niveau.setMatieres(matieres);
        }

        Niveau savedNiveau = niveauServicesImplementation.saveNiveau(niveau);
        return new ResponseEntity<>(savedNiveau, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> mettreAJourNiveau(@PathVariable Long id, @Valid @RequestBody Niveau niveauDetails, BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<>("Erreur de validation", HttpStatus.BAD_REQUEST);
        }

        Niveau niveau = niveauServicesImplementation.findNiveauById(id);
        if (niveau == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Niveau not found with id: " + id);
        }

        // Mettre à jour les champs de l'niveau
        niveau.setNiveau(niveauDetails.getNiveau());

        // Handle associations
        if (niveauDetails.getFilieres() != null) {
            Set<Filiere> filieres = new HashSet<>();
            for (Filiere filiere : niveauDetails.getFilieres()) {
                Filiere existingFiliere = filiereServicesImplementation.findFiliereById(filiere.getId());
                if (existingFiliere != null) {
                    filieres.add(existingFiliere);
                }
            }
            niveau.setFilieres(filieres);
        }

        if (niveauDetails.getMatieres() != null) {
            Set<Matiere> matieres = new HashSet<>();
            for (Matiere matiere : niveauDetails.getMatieres()) {
                Matiere existingMatiere = matiereServicesImplementation.findMatiereById(matiere.getId());
                if (existingMatiere != null) {
                    matieres.add(existingMatiere);
                }
            }
            niveau.setMatieres(matieres);
        }

        Niveau updatedNiveau = niveauServicesImplementation.saveNiveau(niveau);
        return new ResponseEntity<>(updatedNiveau, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> supprimerNiveau(@PathVariable Long id) {
        Niveau niveau = niveauServicesImplementation.findNiveauById(id);
        if (niveau == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Niveau not found with id: " + id);
        }

        niveauServicesImplementation.deleteNiveauById(niveau.getId());
        return new ResponseEntity<>("Niveau supprimé avec succès", HttpStatus.OK);
    }

    @GetMapping("/{niveau}")
    public ResponseEntity<Object> findNiveauByNiveau(@PathVariable String niveau){
       Niveau niveau1 = niveauServicesImplementation.findNiveauByNiveau(niveau);
        if (niveau1 != null){
            return new ResponseEntity<>(niveau1, HttpStatus.FOUND);
        }
        return new ResponseEntity<>("Cette filiere n'existe pas", HttpStatus.NOT_FOUND);
    }
}
