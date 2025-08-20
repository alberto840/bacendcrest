package com.project.pet_veteriana.repository;

import com.project.pet_veteriana.entity.MedicalHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicalHistoryRepository extends JpaRepository<MedicalHistory, Integer> {
}
