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

import com.discipline.Services.ServicesImplementations.CycleServicesImplentation;
import com.discipline.entities.Cycle;


@RestController

@RequestMapping("/cycles")
public class CycleRestController {
    @Autowired
    CycleServicesImplentation cycleServicesImplementation;
    @GetMapping

    public List<Cycle> listeCycle() {
        return cycleServicesImplementation.findAllCycles();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAdresse(@PathVariable("id") Long id) {
        Cycle cycle = cycleServicesImplementation.findCycleById(id);
        if (cycle != null) {
            return ResponseEntity.ok(cycle);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Cycle not found with id: " + id);
        }
    }

    @PostMapping
    public ResponseEntity<?> ajouterCycle(@Valid @RequestBody Cycle cycle, BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<>("Erreur de validation", HttpStatus.BAD_REQUEST);
        }

        Cycle savedcycle = cycleServicesImplementation.saveCycle(cycle);

        return new ResponseEntity<>(savedcycle, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> mettreAJourCycle(@PathVariable Long id, @Valid @RequestBody Cycle cycleDetails, BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<>("Erreur de validation", HttpStatus.BAD_REQUEST);
        }

        Cycle cycle = cycleServicesImplementation.findCycleById(id);

        if (cycle == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Cycle not found with id: " + id);
        }

        // Mettre à jour les champs de l'cycle
        cycle.setCycle(cycleDetails.getCycle());

        Cycle updatedSalle = cycleServicesImplementation.saveCycle(cycle);

        return new ResponseEntity<>(updatedSalle, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> supprimerCycle(@PathVariable Long id) {
        Cycle cycle = cycleServicesImplementation.findCycleById(id);

        if (cycle == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Cycle not found with id: " + id);
        }

        cycleServicesImplementation.deleteCycleById(cycle.getId());

        return new ResponseEntity<>("Cycle supprimé avec succès", HttpStatus.OK);
    }

}
