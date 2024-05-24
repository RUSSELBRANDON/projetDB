package com.discipline.Controlers;

import java.util.List;

import javax.validation.Valid;

import com.discipline.Services.ServicesImplementations.AdresseServicesImplementation;
import com.discipline.Services.ServicesImplementations.EnseignantServicesImplementation;
import com.discipline.entities.Adresse;
import com.discipline.entities.Enseignant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/adresses")
public class AdresseRestController {

    @Autowired
    AdresseServicesImplementation adresseServicesImplementation;
    EnseignantServicesImplementation enseignantServicesImplementation;
    @GetMapping

    public List<Adresse> listeAdresses() {
        return adresseServicesImplementation.findAllAdresses();
    }

    @GetMapping("/rechercherUneAdresse/{id}")
    public ResponseEntity<?> getAdresse(@PathVariable("id") Long id) {
        Adresse adresse = adresseServicesImplementation.findAdresseById(id);
        if (adresse != null) {
            return ResponseEntity.ok(adresse);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Adresse not found with id: " + id);
        }
    }

    @PostMapping
    public ResponseEntity<?> ajouterAdresse(@Valid @RequestBody Adresse adresse, BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<>("Erreur de validation", HttpStatus.BAD_REQUEST);
        }

        Adresse savedAdresse = adresseServicesImplementation.saveAdresse(adresse);

        return new ResponseEntity<>(savedAdresse, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> mettreAJourAdresse(@PathVariable Long id, @Valid @RequestBody Adresse adresseDetails, BindingResult result) {
        try {
            if (result.hasErrors()) {
                return new ResponseEntity<>("Erreur de validation", HttpStatus.BAD_REQUEST);
            }
    
            Adresse adresse = adresseServicesImplementation.findAdresseById(id);
    
            if (adresse == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Adresse not found with id: " + id);
            }
    
            // Mettre à jour les champs de l'adresse
            adresse.setAdresseValue(adresseDetails.getAdresseValue());
            adresse.setType_adresse(adresseDetails.getType_adresse());
    
            // Mettre à jour l'enseignant associé
            if (adresseDetails.getEnseignant() != null && adresseDetails.getEnseignant().getId() != null) {
                Long enseignantId = adresseDetails.getEnseignant().getId();
                Enseignant enseignant = enseignantServicesImplementation.findEnseignantById(enseignantId);
                if (enseignant != null) {
                    adresse.setEnseignant(enseignant);
                } else {
                    return new ResponseEntity<>("Enseignant not found with id: " + enseignantId, HttpStatus.NOT_FOUND);
                }
            } else {
                adresse.setEnseignant(null);
            }
    
            Adresse updatedAdresse = adresseServicesImplementation.saveAdresse(adresse);
    
            return new ResponseEntity<>(updatedAdresse, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("An error occurred while updating the address", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    


    @DeleteMapping("/{id}")
    public ResponseEntity<?> supprimerAdresse(@PathVariable Long id) {
        Adresse adresse = adresseServicesImplementation.findAdresseById(id);

        if (adresse == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Adresse not found with id: " + id);
        }

        adresseServicesImplementation.deleteAdresseById(adresse.getId());

        return new ResponseEntity<>("Enseignant supprimé avec succès", HttpStatus.OK);
    }
}
