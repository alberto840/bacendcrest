package com.project.pet_veteriana.repository;

import com.project.pet_veteriana.entity.Providers;
import org.springframework.data.jpa.repository.JpaRepository;
import com.project.pet_veteriana.entity.Users;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface ProvidersRepository extends JpaRepository<Providers, Integer> {

    List<Providers> findByStatusTrue();

    boolean existsByUser_UserId(Integer userId);

    List<Providers> findTop5ByStatusTrueOrderByRatingDesc();

    List<Providers> findTop10ByStatusTrueOrderByCreatedAtDesc();

    // Nuevo método para obtener un proveedor por userId
    Optional<Providers> findByUser_UserId(Integer userId);

    @Modifying
    @Transactional
    @Query("DELETE FROM Providers p WHERE p.user = :user")
    void deleteByUser(Users user);


}
