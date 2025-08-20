package com.project.pet_veteriana.bl;

import com.project.pet_veteriana.dto.OffersDto;
import com.project.pet_veteriana.entity.Offers;
import com.project.pet_veteriana.repository.OffersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OffersBl {

    @Autowired
    private OffersRepository offersRepository;

    @Transactional
    public OffersDto createOffer(OffersDto offersDto) {
        if (offersDto.getName() == null || offersDto.getName().trim().isEmpty()) {
            throw new RuntimeException("El nombre de la oferta es obligatorio");
        }

        Offers offer = new Offers();
        offer.setName(offersDto.getName());
        offer.setDescription(offersDto.getDescription());
        offer.setDiscountType(offersDto.getDiscountType());
        offer.setDiscountValue(offersDto.getDiscountValue());
        offer.setActive(offersDto.getActive());
        offer.setStartDate(offersDto.getStartDate());
        offer.setEndDate(offersDto.getEndDate());

        Offers savedOffer = offersRepository.save(offer);
        return convertToDto(savedOffer);
    }

    public List<OffersDto> getAllOffers() {
        return offersRepository.findAll()
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public OffersDto getOfferById(Integer id) {
        return offersRepository.findById(id)
                .map(this::convertToDto)
                .orElseThrow(() -> new RuntimeException("Oferta no encontrada con el ID: " + id));
    }

    @Transactional
    public OffersDto updateOffer(Integer id, OffersDto offersDto) {
        Offers offer = offersRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Oferta no encontrada con el ID: " + id));

        offer.setName(offersDto.getName());
        offer.setDescription(offersDto.getDescription());
        offer.setDiscountType(offersDto.getDiscountType());
        offer.setDiscountValue(offersDto.getDiscountValue());
        offer.setActive(offersDto.getActive());
        offer.setStartDate(offersDto.getStartDate());
        offer.setEndDate(offersDto.getEndDate());

        Offers updatedOffer = offersRepository.save(offer);
        return convertToDto(updatedOffer);
    }

    @Transactional
    public boolean deleteOffer(Integer id) {
        if (offersRepository.existsById(id)) {
            offersRepository.deleteById(id);
            return true;
        }
        throw new RuntimeException("No se encontró la oferta con ID: " + id);
    }

    private OffersDto convertToDto(Offers offer) {
        return new OffersDto(
                offer.getOfferId(),
                offer.getName(),
                offer.getDescription(),
                offer.getDiscountType(),
                offer.getDiscountValue(),
                offer.getActive(),
                offer.getStartDate(),
                offer.getEndDate()
        );
    }
}
