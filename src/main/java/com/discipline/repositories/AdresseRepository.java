package com.discipline.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.discipline.entities.Adresse;

@RepositoryRestResource
public interface AdresseRepository extends JpaRepository<Adresse,Long> {
}
