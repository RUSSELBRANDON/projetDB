package com.discipline.Controlers;

import java.util.*;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.discipline.Services.ServicesImplementations.CoursServicesImplementation;
import com.discipline.Services.ServicesImplementations.SalleServicesImplementation;
import com.discipline.entities.Cours;
import com.discipline.entities.Salle;


@RestController
@CrossOrigin("http://localhost:5173")
@RequestMapping("/salles")
public class SalleRestController {
    @Autowired
    SalleServicesImplementation salleServicesImplementation;
    @Autowired
    CoursServicesImplementation coursServicesImplementation;

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
    
        // Assurez-vous que chaque cours de la salle nouvellement ajoutée est correctement associé à cette salle
        if (salle.getCours() != null) {
            for (Cours cours : salle.getCours()) {
                cours.setSalle(salle);
            }
        }
    
        // Sauvegarder la salle avec les cours associés
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
    
        // Mettre à jour les champs de la salle
        salle.setSalle(salleDetails.getSalle());
    
        if (salleDetails.getCours() != null) {
            // Associer les cours de salleDetails à la salle mise à jour
            for (Cours coursDetails : salleDetails.getCours()) {
                // Assurez-vous que chaque cours est correctement associé à la salle
                coursDetails.setSalle(salle);
                // Si le cours n'a pas encore d'ID, cela signifie qu'il s'agit d'un nouveau cours, alors sauvegardez-le
                if (coursDetails.getId() == null) {
                    coursServicesImplementation.saveCours(coursDetails);
                }
            }
        }
    
        // Sauvegarder la salle mise à jour avec les cours associés
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

    @GetMapping("salle/{salleNom}")
    public ResponseEntity<Object> findSalleBySalleNom(@PathVariable String salleNom){
        Salle salle = salleServicesImplementation.findSalleBySalleNom(salleNom);
        if (salle != null){
            return new ResponseEntity<>(salle, HttpStatus.FOUND);
        }
        return new ResponseEntity<>("Cette classe n'existe pas", HttpStatus.NOT_FOUND);
    }

}
