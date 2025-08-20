package com.project.pet_veteriana.repository;

import com.project.pet_veteriana.entity.Vaccination;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VaccinationRepository extends JpaRepository<Vaccination, Integer> {
}
