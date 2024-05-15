package com.discipline.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.discipline.entities.Niveau;
@RepositoryRestResource
public interface NiveauRepository extends JpaRepository<Niveau,Long> {
}
