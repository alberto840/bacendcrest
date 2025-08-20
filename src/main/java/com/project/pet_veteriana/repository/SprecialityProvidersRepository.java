package com.project.pet_veteriana.repository;

import com.project.pet_veteriana.entity.Providers;
import com.project.pet_veteriana.entity.SprecialityProviders;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SprecialityProvidersRepository extends JpaRepository<SprecialityProviders, Integer> {

    // Obtener todas las especialidades de un proveedor
    List<SprecialityProviders> findByProvider(Providers provider);

    // Eliminar todas las especialidades asociadas a un proveedor
    void deleteByProvider(Providers provider);

}
