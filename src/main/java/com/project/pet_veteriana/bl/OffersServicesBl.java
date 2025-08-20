// OffersServicesBl.java
package com.project.pet_veteriana.bl;

import com.project.pet_veteriana.dto.OffersServicesDto;
import com.project.pet_veteriana.entity.Offers;
import com.project.pet_veteriana.entity.OffersServices;
import com.project.pet_veteriana.entity.Services;
import com.project.pet_veteriana.repository.OffersRepository;
import com.project.pet_veteriana.repository.OffersServicesRepository;
import com.project.pet_veteriana.repository.ServicesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OffersServicesBl {

    @Autowired
    private OffersServicesRepository offersServicesRepository;

    @Autowired
    private ServicesRepository servicesRepository;

    @Autowired
    private OffersRepository offersRepository;

    @Transactional
    public OffersServicesDto createOfferService(OffersServicesDto dto) {
        Optional<Services> service = servicesRepository.findById(dto.getServiceId());
        Optional<Offers> offer = offersRepository.findById(dto.getOfferId());

        if (service.isEmpty() || offer.isEmpty()) {
            throw new IllegalArgumentException("Service or Offer not found");
        }

        OffersServices offersServices = new OffersServices();
        offersServices.setService(service.get());
        offersServices.setOffer(offer.get());

        OffersServices savedEntity = offersServicesRepository.save(offersServices);
        return convertToDto(savedEntity);
    }

    public List<OffersServicesDto> getAllOfferServices() {
        List<OffersServices> entities = offersServicesRepository.findAll();
        return entities.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    public OffersServicesDto getOfferServiceById(Integer id) {
        Optional<OffersServices> entity = offersServicesRepository.findById(id);
        if (entity.isEmpty()) {
            throw new IllegalArgumentException("Offer-Service relation not found");
        }
        return convertToDto(entity.get());
    }

    @Transactional
    public OffersServicesDto updateOfferService(Integer id, OffersServicesDto dto) {
        Optional<OffersServices> existingEntity = offersServicesRepository.findById(id);
        if (existingEntity.isEmpty()) {
            throw new IllegalArgumentException("Offer-Service relation not found");
        }

        Optional<Services> service = servicesRepository.findById(dto.getServiceId());
        Optional<Offers> offer = offersRepository.findById(dto.getOfferId());

        if (service.isEmpty() || offer.isEmpty()) {
            throw new IllegalArgumentException("Service or Offer not found");
        }

        OffersServices offersServices = existingEntity.get();
        offersServices.setService(service.get());
        offersServices.setOffer(offer.get());

        OffersServices updatedEntity = offersServicesRepository.save(offersServices);
        return convertToDto(updatedEntity);
    }

    @Transactional
    public boolean deleteOfferService(Integer id) {
        if (offersServicesRepository.existsById(id)) {
            offersServicesRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private OffersServicesDto convertToDto(OffersServices entity) {
        return new OffersServicesDto(
                entity.getOffersServicesId(),
                entity.getService().getServiceId(),
                entity.getOffer().getOfferId()
        );
    }
}

