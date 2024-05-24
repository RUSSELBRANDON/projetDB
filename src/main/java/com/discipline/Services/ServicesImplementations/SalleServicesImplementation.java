package com.discipline.Services.ServicesImplementations;

import com.discipline.Services.SalleServices;
import com.discipline.entities.Salle;
import com.discipline.repositories.SalleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;


@Service
public class SalleServicesImplementation implements SalleServices {
    @Autowired
    SalleRepository salleRepository;

    @Override
    public Salle saveSalle(Salle salle) {
        return salleRepository.save(salle);
    }

    @Override
    public Salle updateSalle(Salle salle) {
        return salleRepository.save(salle);
    }

    @Override
    public Salle findSalleById(Long id) {
        return salleRepository.findById(id).get();
    }

    @Override
    public List<Salle> findAllSalles() {
        return salleRepository.findAll();
    }

    @Override
    public void deleteSalleById(Long id) {
        salleRepository.deleteById(id);
    }

    @Override
    public void deleteAllSalles() {
        salleRepository.deleteAll();
    }

    @Override
    public Salle findByClasseName(String salle) {
        List<Salle> salleList = salleRepository.findAll();
        for (Salle salle1 : salleList){
            if (Objects.equals(salle1.getSalle(), salle)){
                return salle1;
            }
        }
        return null;
    }
}
