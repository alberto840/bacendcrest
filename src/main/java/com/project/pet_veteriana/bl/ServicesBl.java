package com.project.pet_veteriana.bl;

import com.project.pet_veteriana.dto.ImageS3Dto;
import com.project.pet_veteriana.dto.ProvidersDto;
import com.project.pet_veteriana.dto.ServicesDto;
import com.project.pet_veteriana.entity.ImageS3;
import com.project.pet_veteriana.entity.Providers;
import com.project.pet_veteriana.entity.Services;
import com.project.pet_veteriana.entity.SubSubCategoria;
import com.project.pet_veteriana.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ServicesBl {

    @Autowired
    private ServicesRepository servicesRepository;

    @Autowired
    private ProvidersRepository providersRepository;

    @Autowired
    private OffersServicesRepository offersServicesRepository;

    @Autowired
    private ServiceAvailabilityRepository serviceAvailabilityRepository;

    @Autowired
    private TransactionHistoryRepository transactionHistoryRepository;

    @Autowired
    private SubSubCategoriaRepository subSubCategoriaRepository;

    @Autowired
    private ImagesS3Bl imagesS3Bl; // Manejo de imágenes en MinIO

    // Crear un servicio con imagen
    @Transactional
    public ServicesDto createServiceWithImage(ServicesDto servicesDto, MultipartFile file) throws Exception {

        Providers provider = null;
        if (servicesDto.getProviderId() != null) {
            provider = providersRepository.findById(servicesDto.getProviderId())
                    .orElseThrow(() -> new IllegalArgumentException("Provider not found with ID: " + servicesDto.getProviderId()));
        }

        ImageS3Dto imageDto = imagesS3Bl.uploadFile(file);

        SubSubCategoria subSubCategoria = null;
        if (servicesDto.getSubSubCategoriaId() != null) {
            subSubCategoria = subSubCategoriaRepository.findById(servicesDto.getSubSubCategoriaId())
                    .orElseThrow(() -> new IllegalArgumentException("SubSubCategoria no encontrada"));
        }

        Services service = new Services();
        service.setServiceName(servicesDto.getServiceName());
        service.setPrice(servicesDto.getPrice());
        service.setDuration(servicesDto.getDuration());
        service.setDescription(servicesDto.getDescription());
        service.setTipoAtencion(servicesDto.getTipoAtencion());
        service.setCreatedAt(LocalDateTime.now());
        service.setStatus(servicesDto.getStatus());
        service.setProvider(provider);
        service.setImage(new ImageS3(imageDto.getImageId(), imageDto.getFileName(), imageDto.getFileType(), imageDto.getSize(), imageDto.getUploadDate()));
        service.setSubSubCategoria(subSubCategoria);

        Services savedService = servicesRepository.save(service);
        return mapToDto(savedService);
    }

    // Obtener todos los servicios
    public List<ServicesDto> getAllServices() {
        return servicesRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    // Obtener un servicio por ID
    public Optional<ServicesDto> getServiceById(Integer id) {
        return servicesRepository.findById(id)
                .map(this::mapToDto);
    }

    // Actualizar un servicio con imagen
    @Transactional
    public Optional<ServicesDto> updateServiceWithImage(Integer id, ServicesDto servicesDto, MultipartFile file) throws Exception {
        Optional<Services> optionalService = servicesRepository.findById(id);

        if (optionalService.isEmpty()) {
            return Optional.empty();
        }

        Services service = optionalService.get();

        if (file != null && !file.isEmpty()) {
            ImageS3Dto imageDto = imagesS3Bl.uploadFile(file);
            service.setImage(new ImageS3(imageDto.getImageId(), imageDto.getFileName(), imageDto.getFileType(), imageDto.getSize(), imageDto.getUploadDate()));
        }

        Providers provider = providersRepository.findById(servicesDto.getProviderId())
                .orElseThrow(() -> new RuntimeException("Provider not found"));

        SubSubCategoria subSubCategoria = null;
        if (servicesDto.getSubSubCategoriaId() != null) {
            subSubCategoria = subSubCategoriaRepository.findById(servicesDto.getSubSubCategoriaId())
                    .orElseThrow(() -> new IllegalArgumentException("SubSubCategoria no encontrada"));
        }
        service.setSubSubCategoria(subSubCategoria);

        service.setServiceName(servicesDto.getServiceName());
        service.setPrice(servicesDto.getPrice());
        service.setDuration(servicesDto.getDuration());
        service.setDescription(servicesDto.getDescription());
        service.setTipoAtencion(servicesDto.getTipoAtencion());
        service.setStatus(servicesDto.getStatus());
        service.setProvider(provider);
        service.setOnSale(servicesDto.getOnSale());

        Services updatedService = servicesRepository.save(service);
        return Optional.of(mapToDto(updatedService));
    }


    // Eliminar un servicio
    @Transactional
    public boolean deleteService(Integer id) {
        Optional<Services> optionalService = servicesRepository.findById(id);

        if (optionalService.isPresent()) {
            Services service = optionalService.get();

            // 1. Eliminar referencias en ServiceAvailability
            serviceAvailabilityRepository.deleteByService(service);

            // 2. Eliminar referencias en OffersServices
            offersServicesRepository.deleteByService(service);

            // 3. Eliminar referencias en TransactionHistory
            transactionHistoryRepository.deleteByService(service);

            // 4. Desvincular la imagen antes de eliminar el servicio
            if (service.getImage() != null) {
                Integer imageId = service.getImage().getImageId();
                service.setImage(null);
                servicesRepository.save(service);

                try {
                    imagesS3Bl.deleteFile(imageId);
                } catch (Exception e) {
                    throw new RuntimeException("Error al eliminar la imagen asociada", e);
                }
            }

            // 5. Finalmente, eliminar el servicio
            servicesRepository.delete(service);
            return true;
        }

        return false;
    }


    // Obtener los servicios recientes (últimos 10 creados)
    public List<ServicesDto> getRecentServices() {
        return servicesRepository.findTop10ByOrderByCreatedAtDesc().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    // Obtener los servicios en oferta
    public List<ServicesDto> getServicesOnOffer() {
        LocalDateTime now = LocalDateTime.now();

        return offersServicesRepository.findByOffer_StartDateBeforeAndOffer_EndDateAfterAndOffer_IsActiveTrue(now, now)
                .stream()
                .map(offersService -> mapToDto(offersService.getService()))
                .collect(Collectors.toList());
    }

    // Obtener los servicios por proveedor
    public List<ServicesDto> getServicesByProvider(Integer providerId) {
        Providers provider = providersRepository.findById(providerId)
                .orElseThrow(() -> new RuntimeException("Provider not found"));

        return servicesRepository.findByProvider(provider).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    // Obtener servicios por tipo de atención (Domicilio, Consultorio, Ambos)
    public List<ServicesDto> getServicesByTipoAtencion(String tipoAtencion) {
        return servicesRepository.findByTipoAtencion(tipoAtencion)
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }
    
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
                0,
                0,
                provider.getReviews(),
                provider.getCity(),
                provider.getCountry(),
                provider.getVerified(),
                provider.getPhone()
        );
    }

    // Mapear entidad a DTO
    private ServicesDto mapToDto(Services service) {
        ProvidersDto providersDto = convertProviderToDto(service.getProvider());
        String imageUrl = null;
        if (service.getImage() != null) {
            imageUrl = imagesS3Bl.generateFileUrl(service.getImage().getFileName());
        }

        return new ServicesDto(
                service.getServiceId(),
                service.getServiceName(),
                service.getPrice(),
                service.getDuration(),
                service.getDescription(),
                service.getCreatedAt(),
                service.getStatus(),
                service.getProvider().getProviderId(),
                providersDto,
                service.getImage() != null ? service.getImage().getImageId() : null,
                imageUrl,
                service.getTipoAtencion(),
                service.getSubSubCategoria() != null ? service.getSubSubCategoria().getSubSubCategoriaId() : null,
                service.getOnSale(),
                service.getCategory() != null ? service.getCategory().getCategoryId() : null
        );
    }
}
