package com.project.pet_veteriana.repository;

import com.project.pet_veteriana.entity.Pets;
import com.project.pet_veteriana.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface PetsRepository extends JpaRepository<Pets, Integer> {

    // Obtener todas las mascotas de un usuario por su ID
    @Query("SELECT p FROM Pets p WHERE p.user.userId = :userId")
    List<Pets> findByUserId(@Param("userId") Integer userId);

    @Modifying
    @Query("DELETE FROM VaccinationSchedule v WHERE v.pet.petId = :petId")
    void deleteVaccinationSchedulesByPetId(@Param("petId") Integer petId);

    @Modifying
    @Query("DELETE FROM Pets p WHERE p.user = :user")
    void deleteByUser(@Param("user") Users user);


}
