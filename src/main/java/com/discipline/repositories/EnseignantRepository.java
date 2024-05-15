package com.discipline.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.discipline.entities.Enseignant;
@RepositoryRestResource
public interface EnseignantRepository extends JpaRepository<Enseignant,Long> {
}
