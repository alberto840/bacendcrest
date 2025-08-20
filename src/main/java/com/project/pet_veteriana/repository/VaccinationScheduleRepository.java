package com.project.pet_veteriana.repository;

import com.project.pet_veteriana.entity.VaccinationSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VaccinationScheduleRepository extends JpaRepository<VaccinationSchedule, Integer> {
}
