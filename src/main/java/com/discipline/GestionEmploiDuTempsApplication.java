package com.discipline;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.discipline.entities.Enseignant;import com.discipline.repositories.EnseignantRepository;

@SpringBootApplication
public class GestionEmploiDuTempsApplication {

	public static void main(String[] args) {
		SpringApplication.run(GestionEmploiDuTempsApplication.class, args);
	}
	@Bean
    public CommandLineRunner commandLineRunner(EnseignantRepository enseignantRepository) {
        return args -> {
            Enseignant enseignant = Enseignant.builder()
                    .matricule("uds273932")
                    .nom("ALBERTINE")
                    .prenmon("alberto")
                    .build();
            enseignantRepository.save(enseignant);
        };
    }

	


}
