package com.discipline.Controlers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.discipline.entities.Cours;
import com.discipline.entities.Adresse;
import com.discipline.entities.Salle;
import com.discipline.repositories.SalleRepository;

@RestController
@RequestMapping("/coursinfo")
public class CoursInfoController {

    private final SalleRepository salleRepository;

    @Autowired
    public CoursInfoController(SalleRepository salleRepository) {
        this.salleRepository = salleRepository;
    }

    @PostMapping("/{salleId}")
    public List<Object[]> coursInfo(@PathVariable Long salleId) {
        // Récupérer la salle depuis la base de données en fonction de l'ID
        Salle salle = salleRepository.findById(salleId).orElse(null);
        
        if (salle == null) {
            // Gérer le cas où la salle n'est pas trouvée
            return null;
        } else {
            // Récupérer les cours associés à la salle à partir de la relation OneToMany
            List<Cours> coursSemaine = new ArrayList<>(salle.getCours());

            // Créer une liste pour stocker les informations sur les cours
            List<Object[]> coursInfoList = new ArrayList<>();
            
            // Pour chaque cours, récupérer les informations et les stocker dans la liste
            for (Cours cours : coursSemaine) {
                for (String jourHeure : cours.getJour()) {
                    Object[] coursInfo = new Object[7];
                    coursInfo[0] = cours.getId();
                    coursInfo[1] = cours.getSalle().getSalle();
                    coursInfo[2] = cours.getMatiere().getMatiere();
                    coursInfo[3] = jourHeure.split(":")[0]; // Extraire le jour
                    coursInfo[4] = jourHeure.split(":")[1]; // Extraire l'heure
                    coursInfo[5] = cours.getEnseignant() != null ? cours.getEnseignant().getNom() : null;
                    coursInfo[6] = cours.getEnseignant() != null ? cours.getEnseignant().getAdresses().stream()
                                        .map(Adresse::getAdresse)
                                        .collect(Collectors.joining(", ")) : null;
                    coursInfoList.add(coursInfo);
                }
            }
            return coursInfoList;
        }
    }

    @GetMapping("/{salleId}/matiere/{matiereNom}")
    public List<Object[]> findCoursBySalleAndMatiere(@PathVariable Long salleId, @PathVariable String matiereNom) {
        // Récupérer la salle depuis la base de données en fonction de l'ID
        Salle salle = salleRepository.findById(salleId).orElse(null);
        
        if (salle == null) {
            // Gérer le cas où la salle n'est pas trouvée
            return null;
        } else {
            // Récupérer les cours associés à la salle à partir de la relation OneToMany
            List<Cours> coursSemaine = new ArrayList<>(salle.getCours());

            // Filtrer les cours par le nom de la matière
            List<Cours> filteredCours = coursSemaine.stream()
                .filter(cours -> cours.getMatiere().getMatiere().equalsIgnoreCase(matiereNom))
                .collect(Collectors.toList());

            // Créer une liste pour stocker les informations sur les cours
            List<Object[]> coursInfoList = new ArrayList<>();
            
            // Pour chaque cours filtré, récupérer les informations et les stocker dans la liste
            for (Cours cours : filteredCours) {
                for (String jourHeure : cours.getJour()) {
                    String[] jourHeureParts = jourHeure.split(":");
                    if (jourHeureParts.length >= 2) {
                        Object[] coursInfo = new Object[6];
                        coursInfo[0] = cours.getSalle().getSalle(); // Salle
                        coursInfo[1] = cours.getMatiere().getMatiere(); // Matière
                        coursInfo[2] = jourHeureParts[0]; // Jour
                        coursInfo[3] = jourHeureParts[1]; // Heure
                        coursInfo[4] = cours.getEnseignant() != null ? cours.getEnseignant().getNom() : null; // Enseignant
                        coursInfo[5] = cours.getEnseignant() != null ? cours.getEnseignant().getAdresses().stream()
                                            .map(Adresse::getAdresse)
                                            .collect(Collectors.joining(", ")) : null; // Adresses enseignant
                        coursInfoList.add(coursInfo);
                    }
                }
            }
            return coursInfoList;
        }
    }
}
