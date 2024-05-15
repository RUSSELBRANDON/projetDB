package com.discipline.Services;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.discipline.entities.Enseignant;
import java.util.List;
import com.discipline.repositories.EnseignantRepository;

@RestController
public class EnseignantRestController {
    private EnseignantRepository enseignantRepository;
    public EnseignantRestController(EnseignantRepository enseignantRepository){
        this.enseignantRepository = enseignantRepository;
    }

    @GetMapping("/enseignants")
    public List<Enseignant> enseignantList(){
        return enseignantRepository.findAll();
    }
    
}
