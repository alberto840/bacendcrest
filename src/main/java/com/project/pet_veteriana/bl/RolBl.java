package com.project.pet_veteriana.bl;

import com.project.pet_veteriana.dto.RolDto;
import com.project.pet_veteriana.entity.Rol;
import com.project.pet_veteriana.repository.RolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RolBl {

    @Autowired
    private RolRepository rolRepository;

    // Crear un nuevo rol
    public RolDto createRol(RolDto rolDto) {
        Rol rol = new Rol();
        rol.setName(rolDto.getName());
        rol.setDescription(rolDto.getDescription());
        Rol savedRol = rolRepository.save(rol);
        return mapToDto(savedRol);
    }

    // Obtener todos los roles
    public List<RolDto> getAllRoles() {
        List<Rol> roles = rolRepository.findAll();
        return roles.stream()
                .map(this::mapToDto)
                .toList();
    }

    // Obtener un rol por su ID
    public Optional<RolDto> getRolById(Integer rolId) {
        Optional<Rol> rol = rolRepository.findById(rolId);
        return rol.map(this::mapToDto);
    }

    // Actualizar un rol
    public Optional<RolDto> updateRol(Integer rolId, RolDto rolDto) {
        Optional<Rol> existingRol = rolRepository.findById(rolId);
        if (existingRol.isPresent()) {
            Rol rol = existingRol.get();
            rol.setName(rolDto.getName());
            rol.setDescription(rolDto.getDescription());
            // No es necesario actualizar createdAt, ya que no cambia

            Rol updatedRol = rolRepository.save(rol);
            return Optional.of(mapToDto(updatedRol));
        }
        return Optional.empty();
    }

    // Eliminar un rol
    public boolean deleteRol(Integer rolId) {
        Optional<Rol> existingRol = rolRepository.findById(rolId);
        if (existingRol.isPresent()) {
            rolRepository.delete(existingRol.get());
            return true;
        }
        return false;
    }

    // Mapeo de Rol a RolDto
    private RolDto mapToDto(Rol rol) {
        return new RolDto(
                rol.getRolId(),
                rol.getName(),
                rol.getDescription(),
                rol.getCreatedAt());
    }
}
