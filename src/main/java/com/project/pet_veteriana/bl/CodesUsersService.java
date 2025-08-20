package com.project.pet_veteriana.bl;

import com.project.pet_veteriana.dto.CodesUsersDto;
import com.project.pet_veteriana.entity.CodesUsersEntity;
import com.project.pet_veteriana.entity.PromoCodes;
import com.project.pet_veteriana.entity.Users;
import com.project.pet_veteriana.repository.CodesUsersRepository;
import com.project.pet_veteriana.repository.PromoCodesRepository;
import com.project.pet_veteriana.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CodesUsersService {

    @Autowired
    private CodesUsersRepository codesUsersRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private PromoCodesRepository promoCodesRepository;

    // Obtener todos los registros
    public List<CodesUsersDto> getAllCodesUsers() {
        List<CodesUsersEntity> entities = codesUsersRepository.findAll();
        return entities.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    // Obtener un registro por ID
    public CodesUsersDto getCodesUsersById(Integer id) {
        Optional<CodesUsersEntity> entity = codesUsersRepository.findById(id);
        return entity.map(this::convertToDto).orElse(null);
    }

    // Obtener todos los cupones adquiridos por un usuario
    public List<CodesUsersDto> getAllByUserId(Integer userId) {
        List<CodesUsersEntity> entities = codesUsersRepository.findByUser_UserId(userId);
        return entities.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    // Obtener todos los cupones de un proveedor
    public List<CodesUsersDto> getAllByProviderId(Integer providerId) {
        List<CodesUsersEntity> entities = codesUsersRepository.findByPromo_Provider_ProviderId(providerId);
        return entities.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    // Crear un nuevo registro
    @Transactional
    public CodesUsersDto createCodesUsers(CodesUsersDto dto) {
        Users user = usersRepository.findById(dto.getUserId()).orElse(null);
        PromoCodes promo = promoCodesRepository.findById(dto.getPromoId()).orElse(null);

        if (user == null || promo == null) {
            throw new RuntimeException("Usuario o promoción no encontrados");
        }

        CodesUsersEntity entity = new CodesUsersEntity();
        entity.setUser(user);
        entity.setPromo(promo);
        CodesUsersEntity savedEntity = codesUsersRepository.save(entity);

        return convertToDto(savedEntity);
    }

    // Actualizar un registro
    @Transactional
    public CodesUsersDto updateCodesUsers(Integer id, CodesUsersDto dto) {
        Optional<CodesUsersEntity> existingEntity = codesUsersRepository.findById(id);

        if (existingEntity.isPresent()) {
            CodesUsersEntity entity = existingEntity.get();

            Users user = usersRepository.findById(dto.getUserId()).orElse(null);
            PromoCodes promo = promoCodesRepository.findById(dto.getPromoId()).orElse(null);

            if (user == null || promo == null) {
                throw new RuntimeException("Usuario o promoción no encontrados");
            }

            entity.setUser(user);
            entity.setPromo(promo);
            CodesUsersEntity updatedEntity = codesUsersRepository.save(entity);

            return convertToDto(updatedEntity);
        } else {
            throw new RuntimeException("Registro no encontrado");
        }
    }

    // Eliminar un registro
    @Transactional
    public void deleteCodesUsers(Integer id) {
        if (!codesUsersRepository.existsById(id)) {
            throw new RuntimeException("Registro no encontrado");
        }
        codesUsersRepository.deleteById(id);
    }

    // Método auxiliar para convertir a DTO    
    private CodesUsersDto convertToDto(CodesUsersEntity entity) {
        PromoCodes promo = entity.getPromo(); // Obtener la promoción

        return new CodesUsersDto(
                entity.getIdCodes(),
                entity.getUser().getUserId(),
                promo.getPromoId(),
                promo.getCode(),
                promo.getDescription(),
                promo.getDiscountType(),
                promo.getDiscountValue(),
                promo.getEndDate()
        );
    }

}
