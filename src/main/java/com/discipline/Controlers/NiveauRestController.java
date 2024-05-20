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

import com.discipline.Services.ServicesImplementations.NiveauServicesImplementation;
import com.discipline.entities.Niveau;


@RestController

@RequestMapping("/niveau")
public class NiveauRestController {
    @Autowired
    NiveauServicesImplementation niveauServicesImplementation;

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

        Niveau savedNiveau = niveauServicesImplementation.saveNiveau(niveau);

        return new ResponseEntity<>(savedNiveau, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> mettreAJourAdresse(@PathVariable Long id, @Valid @RequestBody Niveau niveauDetails, BindingResult result) {
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
        return new ResponseEntity<>("Enseignant supprimé avec succès", HttpStatus.OK);
    }

        

}
