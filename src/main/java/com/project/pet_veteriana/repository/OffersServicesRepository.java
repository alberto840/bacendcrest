package com.project.pet_veteriana.repository;

import com.project.pet_veteriana.entity.Offers;
import com.project.pet_veteriana.entity.OffersServices;
import com.project.pet_veteriana.entity.Services;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface OffersServicesRepository extends JpaRepository<OffersServices, Integer> {

    // Obtener todos los servicios que están en una oferta activa y dentro del rango de fechas
    List<OffersServices> findByOffer_StartDateBeforeAndOffer_EndDateAfterAndOffer_IsActiveTrue(LocalDateTime startDate, LocalDateTime endDate);

    // Obtener todas las relaciones de una oferta específica
    List<OffersServices> findByOffer(Offers offer);

    // Obtener todas las ofertas de un servicio específico
    List<OffersServices> findByService(Services service);

    void deleteByService(Services service);
}
