package com.project.pet_veteriana.repository;

import com.project.pet_veteriana.entity.OffersProducts;
import com.project.pet_veteriana.entity.Products;
import com.project.pet_veteriana.entity.Services;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface OffersProductsRepository extends JpaRepository<OffersProducts, Integer> {

    // Obtener todos los productos que están en una oferta activa y dentro del rango de fechas
    List<OffersProducts> findByOffer_StartDateBeforeAndOffer_EndDateAfterAndOffer_IsActiveTrue(LocalDateTime startDate, LocalDateTime endDate);

    void deleteByProduct(Products product);
}
