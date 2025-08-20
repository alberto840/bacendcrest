package com.project.pet_veteriana.repository;

import com.project.pet_veteriana.entity.Specialty;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpecialtyRepository extends JpaRepository<Specialty, Integer> {
}
