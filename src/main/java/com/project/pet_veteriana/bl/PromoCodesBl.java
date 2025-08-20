package com.project.pet_veteriana.bl;

import com.project.pet_veteriana.dto.PromoCodesDto;
import com.project.pet_veteriana.entity.PromoCodes;
import com.project.pet_veteriana.entity.Providers;
import com.project.pet_veteriana.repository.PromoCodesRepository;
import com.project.pet_veteriana.repository.ProvidersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PromoCodesBl {

    @Autowired
    private PromoCodesRepository promoCodesRepository;

    @Autowired
    private ProvidersRepository providersRepository;

    // Crear un nuevo código promocional
    @Transactional
    public PromoCodesDto createPromoCode(PromoCodesDto promoCodesDto) {
        Providers provider = null;
        if (promoCodesDto.getProviderId() != null) {
            Optional<Providers> providerOptional = providersRepository.findById(promoCodesDto.getProviderId());
            if (providerOptional.isEmpty()) {
                throw new IllegalArgumentException("Proveedor no encontrado con ID: " + promoCodesDto.getProviderId());
            }
            provider = providerOptional.get();
        }

        PromoCodes promoCode = new PromoCodes();
        promoCode.setCode(promoCodesDto.getCode());
        promoCode.setDescription(promoCodesDto.getDescription());
        promoCode.setDiscountType(promoCodesDto.getDiscountType());
        promoCode.setDiscountValue(promoCodesDto.getDiscountValue());
        promoCode.setMaxUses(promoCodesDto.getMaxUses());
        promoCode.setCurrentUses(0); // Inicialmente en 0
        promoCode.setStartDate(promoCodesDto.getStartDate());
        promoCode.setEndDate(promoCodesDto.getEndDate());
        promoCode.setActive(promoCodesDto.getActive());
        promoCode.setProvider(provider); // puede ser null
        promoCode.setCreatedAt(LocalDateTime.now());

        PromoCodes savedPromoCode = promoCodesRepository.save(promoCode);
        return convertToDto(savedPromoCode);
    }

    // Obtener todos los códigos promocionales
    public List<PromoCodesDto> getAllPromoCodes() {
        List<PromoCodes> promoCodes = promoCodesRepository.findAll();
        return promoCodes.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    // Obtener código promocional por ID
    public PromoCodesDto getPromoCodeById(Integer id) {
        Optional<PromoCodes> promoCodeOptional = promoCodesRepository.findById(id);
        if (promoCodeOptional.isEmpty()) {
            throw new IllegalArgumentException("Código promocional no encontrado");
        }
        return convertToDto(promoCodeOptional.get());
    }

    // Actualizar un código promocional
    @Transactional
    public PromoCodesDto updatePromoCode(Integer id, PromoCodesDto promoCodesDto) {
        Optional<PromoCodes> promoCodeOptional = promoCodesRepository.findById(id);
        if (promoCodeOptional.isEmpty()) {
            throw new IllegalArgumentException("Código promocional no encontrado");
        }

        PromoCodes promoCode = promoCodeOptional.get();

        Providers provider = null;
        if (promoCodesDto.getProviderId() != null) {
            Optional<Providers> providerOptional = providersRepository.findById(promoCodesDto.getProviderId());
            if (providerOptional.isEmpty()) {
                throw new IllegalArgumentException("Proveedor no encontrado con ID: " + promoCodesDto.getProviderId());
            }
            provider = providerOptional.get();
        }

        promoCode.setCode(promoCodesDto.getCode());
        promoCode.setDescription(promoCodesDto.getDescription());
        promoCode.setDiscountType(promoCodesDto.getDiscountType());
        promoCode.setDiscountValue(promoCodesDto.getDiscountValue());
        promoCode.setMaxUses(promoCodesDto.getMaxUses());
        promoCode.setCurrentUses(promoCodesDto.getCurrentUses());
        promoCode.setStartDate(promoCodesDto.getStartDate());
        promoCode.setEndDate(promoCodesDto.getEndDate());
        promoCode.setActive(promoCodesDto.getActive());
        promoCode.setCreatedAt(promoCodesDto.getCreatedAt());
        promoCode.setProvider(provider); // puede ser null o actualizado

        PromoCodes updatedPromoCode = promoCodesRepository.save(promoCode);
        return convertToDto(updatedPromoCode);
    }

    // Eliminar un código promocional
    @Transactional
    public boolean deletePromoCode(Integer id) {
        if (promoCodesRepository.existsById(id)) {
            promoCodesRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Obtener el número de usos actuales de un código promocional
    public Integer getCurrentUses(Integer promoId) {
        Optional<PromoCodes> promoCodeOptional = promoCodesRepository.findById(promoId);
        if (promoCodeOptional.isEmpty()) {
            throw new IllegalArgumentException("Código promocional no encontrado");
        }
        return promoCodeOptional.get().getCurrentUses();
    }

    // Incrementar el contador de usos de un código promocional
    public PromoCodesDto incrementPromoCodeUsage(Integer promoId) {
        Optional<PromoCodes> promoCodeOptional = promoCodesRepository.findById(promoId);
        if (promoCodeOptional.isEmpty()) {
            throw new IllegalArgumentException("Código promocional no encontrado");
        }

        PromoCodes promoCode = promoCodeOptional.get();
        int current = promoCode.getCurrentUses();

        if (current >= promoCode.getMaxUses()) {
            throw new IllegalStateException("Este código ya alcanzó su máximo de usos.");
        }

        promoCode.setCurrentUses(current + 1);
        promoCode.setUpdatedAt(LocalDateTime.now()); // Si tienes esta columna
        PromoCodes updatedPromoCode = promoCodesRepository.save(promoCode);

        return convertToDto(updatedPromoCode);
    }



    // Convertir entidad a DTO
    private PromoCodesDto convertToDto(PromoCodes promoCode) {
        return new PromoCodesDto(
                promoCode.getPromoId(),
                promoCode.getCode(),
                promoCode.getDescription(),
                promoCode.getDiscountType(),
                promoCode.getDiscountValue(),
                promoCode.getMaxUses(),
                promoCode.getCurrentUses(),
                promoCode.getStartDate(),
                promoCode.getEndDate(),
                promoCode.getActive(),
                promoCode.getProvider() != null ? promoCode.getProvider().getProviderId() : null,
                promoCode.getCreatedAt()
        );
    }
}
