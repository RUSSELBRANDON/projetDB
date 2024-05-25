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
import com.discipline.Services.ServicesImplementations.CycleServicesImplentation;

import com.discipline.entities.Cycle;
import com.discipline.entities.Filiere;
import com.discipline.entities.Niveau;

@RestController
@RequestMapping("/filieres")
public class FiliereRestController {
    @Autowired
    FiliereServicesImplementation filiereServicesImplementation;

    @Autowired
    NiveauServicesImplementation niveauServicesImplementation;

    @Autowired
    CycleServicesImplentation cycleServicesImplentation;

    @GetMapping
    public List<Filiere> listeFiliere() {
        return filiereServicesImplementation.findAllFilieres();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getFiliere(@PathVariable("id") Long id) {
        Filiere filiere = filiereServicesImplementation.findFiliereById(id);
        if (filiere != null) {
            return ResponseEntity.ok(filiere);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Filiere not found with id: " + id);
        }
    }

    @PostMapping
    public ResponseEntity<?> ajouterFiliere(@Valid @RequestBody Filiere filiere, BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<>("Erreur de validation", HttpStatus.BAD_REQUEST);
        }

        // Handle associations
        if (filiere.getNiveaux() != null) {
            Set<Niveau> niveaux = new HashSet<>();
            for (Niveau niveau : filiere.getNiveaux()) {
                Niveau existingNiveau = niveauServicesImplementation.findNiveauById(niveau.getId());
                if (existingNiveau != null) {
                    niveaux.add(existingNiveau);
                }
            }
            filiere.setNiveaux(niveaux);
        }

        // Handle associations
        if (filiere.getCycles() != null) {
            Set<Cycle> cycles = new HashSet<>();
            for (Cycle cycle : filiere.getCycles()) {
                Cycle existingCycles =cycleServicesImplentation.findCycleById(cycle.getId());
                if (existingCycles != null) {
                    cycles.add(existingCycles);
                }
            }
            filiere.setCycles(cycles);
        }

        Filiere savedFiliere = filiereServicesImplementation.saveFiliere(filiere);
        return new ResponseEntity<>(savedFiliere, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> mettreAJourFiliere(@PathVariable Long id, @Valid @RequestBody Filiere filiereDetails, BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<>("Erreur de validation", HttpStatus.BAD_REQUEST);
        }

        Filiere filiere = filiereServicesImplementation.findFiliereById(id);
        if (filiere == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Filiere not found with id: " + id);
        }

        // Mettre à jour les champs de la filiere
        filiere.setNom(filiereDetails.getNom());
        filiere.setAnnee_scolaire(filiereDetails.getAnnee_scolaire());
        filiere.setNbre_place(filiereDetails.getNbre_place());

        // Handle associations
        if (filiereDetails.getNiveaux() != null) {
            Set<Niveau> niveaux = new HashSet<>();
            for (Niveau niveau : filiereDetails.getNiveaux()) {
                Niveau existingNiveau = niveauServicesImplementation.findNiveauById(niveau.getId());
                if (existingNiveau != null) {
                    niveaux.add(existingNiveau);
                }
            }
            filiere.setNiveaux(niveaux);
        }

        // Handle associations
        if (filiereDetails.getCycles() != null) {
            Set<Cycle> cycles = new HashSet<>();
            for (Cycle cycle : filiereDetails.getCycles()) {
                Cycle existingCycle =cycleServicesImplentation.findCycleById(cycle.getId());
                if (existingCycle != null) {
                    cycles.add(existingCycle);
                }
            }
            filiere.setCycles(cycles);
        }

        Filiere updatedFiliere = filiereServicesImplementation.saveFiliere(filiere);
        return new ResponseEntity<>(updatedFiliere, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> supprimerFiliere(@PathVariable Long id) {
        Filiere filiere = filiereServicesImplementation.findFiliereById(id);
        if (filiere == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Filiere not found with id: " + id);
        }

        filiereServicesImplementation.deleteFiliereById(filiere.getId());
        return new ResponseEntity<>("Filiere supprimé avec succès", HttpStatus.OK);
    }

    @GetMapping("nom/{nom}")
    public ResponseEntity<Object> findFiliereByNom(@PathVariable String nom){
        Filiere filiere = filiereServicesImplementation.findFiliereByNom(nom);
        if (filiere != null) {
            // Créer une map pour stocker le nom de la filière
            Map<String, Object> filiereInfo = new HashMap<>();
            filiereInfo.put("nom", filiere.getNom());
    
            // Retourner le nom de la filière dans la réponse
            return new ResponseEntity<>(filiereInfo, HttpStatus.OK);
        }
        return new ResponseEntity<>("Cette filière n'existe pas", HttpStatus.NOT_FOUND);
}

}
