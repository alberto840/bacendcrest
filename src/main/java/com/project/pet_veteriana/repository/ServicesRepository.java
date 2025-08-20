package com.project.pet_veteriana.repository;

import com.project.pet_veteriana.entity.Providers;
import com.project.pet_veteriana.entity.Services;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ServicesRepository extends JpaRepository<Services, Integer> {

    // Obtener los 10 servicios más recientes
    List<Services> findTop10ByOrderByCreatedAtDesc();

    // Obtener todos los servicios de un proveedor específico
    List<Services> findByProvider(Providers provider);

    int countByProvider(Providers provider);
    List<Services> findByTipoAtencion(String tipoAtencion);
}
