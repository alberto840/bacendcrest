package com.project.pet_veteriana.bl;

import com.project.pet_veteriana.dto.ServiceAvailabilityDto;
import com.project.pet_veteriana.entity.ServiceAvailability;
import com.project.pet_veteriana.entity.Services;
import com.project.pet_veteriana.repository.ServiceAvailabilityRepository;
import com.project.pet_veteriana.repository.ServicesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ServiceAvailabilityBl {

    @Autowired
    private ServiceAvailabilityRepository serviceAvailabilityRepository;

    @Autowired
    private ServicesRepository servicesRepository;

    // Obtener horarios disponibles de un servicio
    public List<ServiceAvailabilityDto> getAvailableHoursByService(Integer serviceId) {
        Services service = servicesRepository.findById(serviceId)
                .orElseThrow(() -> new RuntimeException("Service not found"));

        return serviceAvailabilityRepository.findByServiceAndIsReservedFalse(service)
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    // Agregar múltiples horarios disponibles
    @Transactional
    public List<ServiceAvailabilityDto> addAvailableHoursBulk(Integer serviceId, List<LocalTime> availableHours) {
        Services service = servicesRepository.findById(serviceId)
                .orElseThrow(() -> new RuntimeException("Service not found"));

        List<ServiceAvailability> availabilities = availableHours.stream()
                .map(hour -> new ServiceAvailability(null, service, hour, false))
                .collect(Collectors.toList());

        List<ServiceAvailability> savedAvailabilities = serviceAvailabilityRepository.saveAll(availabilities);

        return savedAvailabilities.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    // Marcar un horario como reservado
    public ServiceAvailabilityDto markAsReserved(Integer availabilityId) {
        Optional<ServiceAvailability> optionalAvailability = serviceAvailabilityRepository.findById(availabilityId);
        if (optionalAvailability.isPresent()) {
            ServiceAvailability availability = optionalAvailability.get();
            availability.setIsReserved(true);
            serviceAvailabilityRepository.save(availability);
            return mapToDto(availability);
        }
        throw new RuntimeException("Availability not found");
    }

    // Eliminar un horario disponible
    public boolean deleteAvailability(Integer availabilityId) {
        Optional<ServiceAvailability> optionalAvailability = serviceAvailabilityRepository.findById(availabilityId);
        if (optionalAvailability.isPresent()) {
            serviceAvailabilityRepository.delete(optionalAvailability.get());
            return true;
        }
        return false;
    }

    private ServiceAvailabilityDto mapToDto(ServiceAvailability availability) {
        return new ServiceAvailabilityDto(
                availability.getAvailabilityId(),
                availability.getService().getServiceId(),
                availability.getAvailableHour(),
                availability.getIsReserved()
        );
    }
}
