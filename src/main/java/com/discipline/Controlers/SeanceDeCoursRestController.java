package com.discipline.Controlers;

import com.discipline.Services.ServicesImplementations.SeanceDeCoursServicesImplementation;
import com.discipline.entities.Enseignant;
import com.discipline.entities.SeanceDeCours;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/seances")
public class SeanceDeCoursRestController {

    @Autowired
    SeanceDeCoursServicesImplementation seanceDeCoursServicesImplementation;

    @GetMapping
    public List<SeanceDeCours> getSeanceDeCoursList(@RequestParam String classe, @RequestParam String matiere){
        return seanceDeCoursServicesImplementation.getSeancesDeCours(classe, matiere);
    }

    @PostMapping
    public ResponseEntity<?> ajouterSeanceDeCours(@RequestBody SeanceDeCours seanceDeCours, BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<>("Erreur de validation", HttpStatus.BAD_REQUEST);
        }

        SeanceDeCours seanceDeCours1 = seanceDeCoursServicesImplementation.ajouterSeanceDeCours(seanceDeCours);

        return new ResponseEntity<>(seanceDeCours1, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> supprimerSeanceDeCours(@PathVariable Long id) {

        if (!seanceDeCoursServicesImplementation.ifExistsBySeanceDeCoursId(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Séance not found with id: " + id);
        }

        Optional<SeanceDeCours> seanceDeCours = seanceDeCoursServicesImplementation.rechercherById(id);
        seanceDeCoursServicesImplementation.supprimerSeanceDeCours(id);

        return new ResponseEntity<>("Séance supprimée avec succès", HttpStatus.OK);
    }


    @DeleteMapping
    public void supprimerToutesLesSeances(){
        seanceDeCoursServicesImplementation.supprimerToutesLesSeances();
    }
}
