package com.project.pet_veteriana.bl;

import com.project.pet_veteriana.dto.SprecialityProvidersDto;
import com.project.pet_veteriana.entity.SprecialityProviders;
import com.project.pet_veteriana.entity.Providers;
import com.project.pet_veteriana.entity.Specialty;
import com.project.pet_veteriana.repository.SprecialityProvidersRepository;
import com.project.pet_veteriana.repository.ProvidersRepository;
import com.project.pet_veteriana.repository.SpecialtyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SpecialityProvidersBl {

    @Autowired
    private SprecialityProvidersRepository sprecialityProvidersRepository;

    @Autowired
    private SpecialtyRepository specialtyRepository;

    @Autowired
    private ProvidersRepository providersRepository;

    // Crear una nueva relación especialidad-proveedor
    @Transactional
    public SprecialityProvidersDto createSpecialityProvider(SprecialityProvidersDto dto) {
        Optional<Specialty> specialtyOptional = specialtyRepository.findById(dto.getSpecialtyId());
        Optional<Providers> providerOptional = providersRepository.findById(dto.getProviderId());

        if (specialtyOptional.isEmpty()) {
            throw new IllegalArgumentException("Especialidad no encontrada");
        }

        if (providerOptional.isEmpty()) {
            throw new IllegalArgumentException("Proveedor no encontrado");
        }

        SprecialityProviders sprecialityProviders = new SprecialityProviders();
        sprecialityProviders.setSpecialty(specialtyOptional.get());
        sprecialityProviders.setProvider(providerOptional.get());

        SprecialityProviders savedEntity = sprecialityProvidersRepository.save(sprecialityProviders);
        return convertToDto(savedEntity);
    }

    // Obtener todas las relaciones especialidad-proveedor
    public List<SprecialityProvidersDto> getAllSpecialityProviders() {
        List<SprecialityProviders> entities = sprecialityProvidersRepository.findAll();
        return entities.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    // Obtener una relación por ID
    public SprecialityProvidersDto getSpecialityProviderById(Integer id) {
        Optional<SprecialityProviders> entityOptional = sprecialityProvidersRepository.findById(id);
        if (entityOptional.isEmpty()) {
            throw new IllegalArgumentException("Relación especialidad-proveedor no encontrada");
        }
        return convertToDto(entityOptional.get());
    }

    // Actualizar una relación especialidad-proveedor
    @Transactional
    public SprecialityProvidersDto updateSpecialityProvider(Integer id, SprecialityProvidersDto dto) {
        Optional<SprecialityProviders> entityOptional = sprecialityProvidersRepository.findById(id);
        if (entityOptional.isEmpty()) {
            throw new IllegalArgumentException("Relación especialidad-proveedor no encontrada");
        }

        Optional<Specialty> specialtyOptional = specialtyRepository.findById(dto.getSpecialtyId());
        Optional<Providers> providerOptional = providersRepository.findById(dto.getProviderId());

        if (specialtyOptional.isEmpty()) {
            throw new IllegalArgumentException("Especialidad no encontrada");
        }

        if (providerOptional.isEmpty()) {
            throw new IllegalArgumentException("Proveedor no encontrado");
        }

        SprecialityProviders entity = entityOptional.get();
        entity.setSpecialty(specialtyOptional.get());
        entity.setProvider(providerOptional.get());

        SprecialityProviders updatedEntity = sprecialityProvidersRepository.save(entity);
        return convertToDto(updatedEntity);
    }

    // Eliminar una relación especialidad-proveedor
    @Transactional
    public boolean deleteSpecialityProvider(Integer id) {
        if (sprecialityProvidersRepository.existsById(id)) {
            sprecialityProvidersRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Convertir entidad a DTO
    private SprecialityProvidersDto convertToDto(SprecialityProviders entity) {
        return new SprecialityProvidersDto(
                entity.getIdSpPro(),
                entity.getSpecialty().getSpecialtyId(),
                entity.getProvider().getProviderId()
        );
    }
}
