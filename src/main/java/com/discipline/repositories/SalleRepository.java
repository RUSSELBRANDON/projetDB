package com.discipline.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.discipline.entities.Salle;
@RepositoryRestResource
public interface SalleRepository extends JpaRepository<Salle,Long>{

}
