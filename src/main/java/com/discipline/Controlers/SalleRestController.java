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

import com.discipline.Services.ServicesImplementations.SalleServicesImplementation;
import com.discipline.entities.Salle;


@RestController

@RequestMapping("/salles")
public class SalleRestController {
    @Autowired
    SalleServicesImplementation salleServicesImplementation;

    @GetMapping

    public List<Salle> listeNiveau() {
        return salleServicesImplementation.findAllSalles();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getNivaeu(@PathVariable("id") Long id) {
        Salle salle = salleServicesImplementation.findSalleById(id);
        if (salle != null) {
            return ResponseEntity.ok(salle);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Salle not found with id: " + id);
        }
    }

    @PostMapping
    public ResponseEntity<?> ajouterNiveau(@Valid @RequestBody Salle salle, BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<>("Erreur de validation", HttpStatus.BAD_REQUEST);
        }

        Salle savedSalle = salleServicesImplementation.saveSalle(salle);

        return new ResponseEntity<>(savedSalle, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> mettreAJourAdresse(@PathVariable Long id, @Valid @RequestBody Salle salleDetails, BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<>("Erreur de validation", HttpStatus.BAD_REQUEST);
        }

        Salle salle = salleServicesImplementation.findSalleById(id);

        if (salle == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Salle not found with id: " + id);
        }

        // Mettre à jour les champs de l'salle
        salle.setSalle(salleDetails.getSalle());

        Salle updatedSalle = salleServicesImplementation.saveSalle(salle);

        return new ResponseEntity<>(updatedSalle, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> supprimerSalle(@PathVariable Long id) {
        Salle salle = salleServicesImplementation.findSalleById(id);

        if (salle == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Salle not found with id: " + id);
        }

        salleServicesImplementation.deleteSalleById(salle.getId());
        return new ResponseEntity<>("Enseignant supprimé avec succès", HttpStatus.OK);
    }

}
