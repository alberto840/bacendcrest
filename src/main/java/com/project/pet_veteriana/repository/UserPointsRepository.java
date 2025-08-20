package com.project.pet_veteriana.repository;

import com.project.pet_veteriana.entity.UserPoints;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserPointsRepository extends JpaRepository<UserPoints, Integer> {
}
