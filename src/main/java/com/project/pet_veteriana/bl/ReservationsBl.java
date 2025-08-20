package com.project.pet_veteriana.bl;

import com.project.pet_veteriana.dto.ReservationsDto;
import com.project.pet_veteriana.dto.UsersDto;
import com.project.pet_veteriana.dto.ServicesDto;
import com.project.pet_veteriana.dto.ServiceAvailabilityDto;
import com.project.pet_veteriana.dto.PetsDto;
import com.project.pet_veteriana.dto.ProvidersDto; // Necesario si ServicesDto o ProductsDto necesitan ProvidersDto

import com.project.pet_veteriana.entity.*;
import com.project.pet_veteriana.repository.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReservationsBl {

    @Autowired
    private ReservationsRepository reservationsRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private ServicesRepository servicesRepository;

    @Autowired
    private PetsRepository petRepository;

    @Autowired
    private ServiceAvailabilityRepository availabilityRepository;

    @Autowired
    private ImagesS3Bl imagesS3Bl;

    @Autowired
    private ProvidersRepository providersRepository;

    /**
     * Converts a Users entity to a UsersDto.
     * @param user The Users entity.
     * @return The corresponding UsersDto.
     */
    private UsersDto convertUserToDto(Users user) {
        if (user == null) {
            return null;
        }

        String imageUrl = null;
        if (user.getImage() != null) {
            imageUrl = imagesS3Bl.generateFileUrl(user.getImage().getFileName());
        }

        Integer rolId = (user.getRol() != null) ? user.getRol().getRolId() : null;
        Integer imageId = (user.getImage() != null) ? user.getImage().getImageId() : null;

        Integer providerId = null;
        // Check if the user is associated with a provider
        Optional<Providers> provider = providersRepository.findByUser_UserId(user.getUserId());
        if (provider.isPresent()) {
            providerId = provider.get().getProviderId();
        }

        return new UsersDto(
                user.getUserId(),
                user.getName(),
                user.getEmail(),
                null, // Password is not exposed in DTO
                user.getPhoneNumber(),
                user.getLocation(),
                user.getPreferredLanguage(),
                user.getLastLogin(),
                user.getCreatedAt(),
                user.getStatus(),
                rolId,
                imageId,
                imageUrl,
                providerId
        );
    }

    /**
     * Converts a Providers entity to a ProvidersDto.
     * @param provider The Providers entity.
     * @return The corresponding ProvidersDto.
     */
    private ProvidersDto convertProviderToDto(Providers provider) {
        if (provider == null) {
            return null;
        }

        String imageUrl = null;
        if (provider.getImage() != null) {
            imageUrl = imagesS3Bl.generateFileUrl(provider.getImage().getFileName());
        }

        return new ProvidersDto(
                provider.getProviderId(),
                provider.getUser().getUserId(),
                provider.getName(),
                provider.getDescription(),
                provider.getAddress(),
                imageUrl,
                provider.getRating(),
                provider.getCreatedAt(),
                provider.getUpdatedAt(),
                provider.getStatus(),
                0, // Assuming these are placeholder or calculated values
                0, // Assuming these are placeholder or calculated values
                provider.getReviews(),
                provider.getCity(),
                provider.getCountry(),
                provider.getVerified(),
                provider.getPhone()
        );
    }

    /**
     * Converts a Services entity to a ServicesDto.
     * @param service The Services entity.
     * @return The corresponding ServicesDto.
     */
    private ServicesDto convertServiceToDto(Services service) {
        if (service == null) {
            return null;
        }

        String imageUrl = null;
        if (service.getImage() != null) {
            imageUrl = imagesS3Bl.generateFileUrl(service.getImage().getFileName());
        }
        ProvidersDto providersDto = convertProviderToDto(service.getProvider());

        Integer providerId = (service.getProvider() != null) ? service.getProvider().getProviderId() : null;
        Integer categoryId = (service.getCategory() != null) ? service.getCategory().getCategoryId() : null;
        Integer subSubCategoriaId = (service.getSubSubCategoria() != null) ? service.getSubSubCategoria().getSubSubCategoriaId() : null;

        return new ServicesDto(
                service.getServiceId(),
                service.getServiceName(),
                service.getPrice(),
                service.getDuration(),
                service.getDescription(),
                service.getCreatedAt(),
                service.getStatus(),
                providerId,
                providersDto,
                service.getImage() != null ? service.getImage().getImageId() : null,
                imageUrl,
                service.getTipoAtencion(),
                subSubCategoriaId,
                service.getOnSale(),
                categoryId
        );
    }

    /**
     * Converts a ServiceAvailability entity to a ServiceAvailabilityDto.
     * This method now uses the provided ServiceAvailabilityDto structure.
     * @param availability The ServiceAvailability entity.
     * @return The corresponding ServiceAvailabilityDto.
     */
    private ServiceAvailabilityDto convertAvailabilityToDto(ServiceAvailability availability) {
        if (availability == null) {
            return null;
        }
        return new ServiceAvailabilityDto(
                availability.getAvailabilityId(),
                availability.getService() != null ? availability.getService().getServiceId() : null,
                availability.getAvailableHour(), // Changed from getDate() to getAvailableHour() based on your DTO
                availability.getIsReserved()
        );
    }

    /**
     * Converts a Pets entity to a PetsDto.
     * This method now uses the provided PetsDto structure.
     * @param pet The Pets entity.
     * @return The corresponding PetsDto.
     */
    private PetsDto convertPetToDto(Pets pet) {
        if (pet == null) {
            return null;
        }

        String imageUrl = null;
        if (pet.getImage() != null) {
            imageUrl = imagesS3Bl.generateFileUrl(pet.getImage().getFileName());
        }

        return new PetsDto(
                pet.getPetId(),
                pet.getPetName(),       // Assuming Pet entity has getName() for petName
                pet.getPetBreed(),
                pet.getPetAge(),
                pet.getCreatedAt(),
                pet.getWeight(),     // Assuming Pet entity has getWeight()
                pet.getHeight(),     // Assuming Pet entity has getHeight()
                pet.getGender(),     // Assuming Pet entity has getGender()
                pet.getAllergies(),  // Assuming Pet entity has getAllergies()
                pet.getBehaviorNotes(), // Assuming Pet entity has getBehaviorNotes()
                pet.getUser() != null ? pet.getUser().getUserId() : null, // Assuming owner is the user
                pet.getImage() != null ? pet.getImage().getImageId() : null,
                imageUrl,
                pet.getBirthDate(),  // Assuming Pet entity has getBirthDate()
                pet.getSpecies()     // Assuming Pet entity has getSpecies()
        );
    }

    @Transactional
    public ReservationsDto createReservation(ReservationsDto dto) {
        Users user = usersRepository.findById(dto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        Services service = servicesRepository.findById(dto.getServiceId())
                .orElseThrow(() -> new IllegalArgumentException("Servicio no encontrado"));

        ServiceAvailability availability = availabilityRepository.findById(dto.getAvailabilityId())
                .orElseThrow(() -> new IllegalArgumentException("Horario no disponible"));

        Pets pet = petRepository.findById(dto.getPetId())
                .orElseThrow(() -> new IllegalArgumentException("Mascota no encontrada"));

        if (availability.getIsReserved()) {
            throw new IllegalArgumentException("Este horario ya está reservado");
        }

        availability.setIsReserved(true);
        availabilityRepository.save(availability);

        Reservations reservation = new Reservations();
        reservation.setUser(user);
        reservation.setService(service);
        reservation.setAvailability(availability);
        reservation.setPet(pet);
        reservation.setDate(dto.getDate());
        reservation.setStatus("PENDIENTE");
        reservation.setCreatedAt(LocalDateTime.now());

        Reservations savedReservation = reservationsRepository.save(reservation);
        return convertToDto(savedReservation);
    }

    public List<ReservationsDto> getAllReservations() {
        List<Reservations> reservations = reservationsRepository.findAll();
        return reservations.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    public ReservationsDto getReservationById(Integer id) {
        Reservations reservation = reservationsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Reservación no encontrada"));
        return convertToDto(reservation);
    }

    public List<ReservationsDto> getByIdUser(Integer userId) {
        List<Reservations> reservations = reservationsRepository.findByUser_UserId(userId);
        return reservations.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    public List<ReservationsDto> getByIdProvider(Integer providerId) {
        List<Reservations> reservations = reservationsRepository.findByService_Provider_ProviderId(providerId);
        return reservations.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @Transactional
    public ReservationsDto updateReservation(Integer id, ReservationsDto dto) {
        Reservations reservation = reservationsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Reservación no encontrada"));

        // Update User
        if (dto.getUserId() != null) {
            Users newUser = usersRepository.findById(dto.getUserId())
                    .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado con ID: " + dto.getUserId()));
            reservation.setUser(newUser);
        } else if (dto.getUser() != null && dto.getUser().getUserId() != null) {
            Users newUser = usersRepository.findById(dto.getUser().getUserId())
                    .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado con ID: " + dto.getUser().getUserId()));
            reservation.setUser(newUser);
        }

        // Update Service
        if (dto.getServiceId() != null) {
            Services newService = servicesRepository.findById(dto.getServiceId())
                    .orElseThrow(() -> new IllegalArgumentException("Servicio no encontrado con ID: " + dto.getServiceId()));
            reservation.setService(newService);
        } else if (dto.getService() != null && dto.getService().getServiceId() != null) {
            Services newService = servicesRepository.findById(dto.getService().getServiceId())
                    .orElseThrow(() -> new IllegalArgumentException("Servicio no encontrado con ID: " + dto.getService().getServiceId()));
            reservation.setService(newService);
        }

        // Update Availability
        if (dto.getAvailabilityId() != null) {
            ServiceAvailability newAvailability = availabilityRepository.findById(dto.getAvailabilityId())
                    .orElseThrow(() -> new IllegalArgumentException("Horario no disponible con ID: " + dto.getAvailabilityId()));
            reservation.setAvailability(newAvailability);
        } else if (dto.getAvailability() != null && dto.getAvailability().getAvailabilityId() != null) {
            ServiceAvailability newAvailability = availabilityRepository.findById(dto.getAvailability().getAvailabilityId())
                    .orElseThrow(() -> new IllegalArgumentException("Horario no disponible con ID: " + dto.getAvailability().getAvailabilityId()));
            reservation.setAvailability(newAvailability);
        }

        // Update Pet
        if (dto.getPetId() != null) {
            Pets newPet = petRepository.findById(dto.getPetId())
                    .orElseThrow(() -> new IllegalArgumentException("Mascota no encontrada con ID: " + dto.getPetId()));
            reservation.setPet(newPet);
        } else if (dto.getPet() != null && dto.getPet().getPetId() != null) {
            Pets newPet = petRepository.findById(dto.getPet().getPetId())
                    .orElseThrow(() -> new IllegalArgumentException("Mascota no encontrada con ID: " + dto.getPet().getPetId()));
            reservation.setPet(newPet);
        }

        // Update other fields
        if (dto.getDate() != null) {
            reservation.setDate(dto.getDate());
        }
        if (dto.getStatus() != null) {
            reservation.setStatus(dto.getStatus());
        }

        Reservations updatedReservation = reservationsRepository.save(reservation);
        return convertToDto(updatedReservation);
    }

    @Transactional
    public boolean deleteReservation(Integer id) {
        Reservations reservation = reservationsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Reservación no encontrada"));

        if (!"PENDIENTE".equals(reservation.getStatus())) {
            throw new IllegalArgumentException("Solo se pueden eliminar reservaciones pendientes");
        }

        ServiceAvailability availability = reservation.getAvailability();
        availability.setIsReserved(false);
        availabilityRepository.save(availability);

        reservationsRepository.delete(reservation);
        return true;
    }

    public ReservationsDto updateReservationStatus(Integer id, String status) {
        Reservations reservation = reservationsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Reservación no encontrada"));

        List<String> validStatuses = List.of("PENDIENTE", "ATENDIDO", "CANCELADO", "REALIZADO");
        if (!validStatuses.contains(status.toUpperCase())) {
            throw new IllegalArgumentException("Estado no válido. Los valores permitidos son: PENDIENTE, ATENDIDO, CANCELADO, REALIZADO");
        }

        reservation.setStatus(status.toUpperCase());
        Reservations updatedReservation = reservationsRepository.save(reservation);
        return convertToDto(updatedReservation);
    }

    /**
     * Converts a Reservations entity to a ReservationsDto, populating nested DTOs.
     * @param reservation The Reservations entity.
     * @return The corresponding ReservationsDto.
     */
    private ReservationsDto convertToDto(Reservations reservation) {
        UsersDto usersDto = convertUserToDto(reservation.getUser());
        ServicesDto servicesDto = convertServiceToDto(reservation.getService());
        ServiceAvailabilityDto availabilityDto = convertAvailabilityToDto(reservation.getAvailability());
        PetsDto petsDto = convertPetToDto(reservation.getPet());

        return new ReservationsDto(
                reservation.getReservationId(),
                // Pass IDs and DTOs as per the DTO constructor
                reservation.getUser() != null ? reservation.getUser().getUserId() : null,
                usersDto,
                reservation.getService() != null ? reservation.getService().getServiceId() : null,
                servicesDto,
                reservation.getAvailability() != null ? reservation.getAvailability().getAvailabilityId() : null,
                availabilityDto,
                reservation.getPet() != null ? reservation.getPet().getPetId() : null,
                petsDto,
                reservation.getDate(),
                reservation.getStatus(),
                reservation.getCreatedAt()
        );
    }
}