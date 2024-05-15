package com.discipline.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.discipline.entities.Cours;
@RepositoryRestResource
public interface CoursRepository extends JpaRepository<Cours,Long> {
}
