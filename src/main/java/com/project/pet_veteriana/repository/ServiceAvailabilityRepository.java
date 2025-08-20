package com.project.pet_veteriana.repository;

import com.project.pet_veteriana.entity.ServiceAvailability;
import com.project.pet_veteriana.entity.Services;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ServiceAvailabilityRepository extends JpaRepository<ServiceAvailability, Integer> {

    // Obtener horarios disponibles (que no han sido reservados)
    List<ServiceAvailability> findByServiceAndIsReservedFalse(Services service);

    // Obtener todos los horarios (incluidos los reservados)
    List<ServiceAvailability> findByService(Services service);

    // Eliminar todos los registros asociados a un servicio
    void deleteByService(Services service);
}

