package com.project.pet_veteriana.controller;

import com.project.pet_veteriana.bl.RolBl;
import com.project.pet_veteriana.dto.ResponseDto;
import com.project.pet_veteriana.dto.RolDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/roles")
public class RolController {

    private static final Logger logger = LoggerFactory.getLogger(RolController.class);

    @Autowired
    private RolBl rolBl;

    // Crear un nuevo rol
    @PostMapping
    public ResponseEntity<ResponseDto<RolDto>> createRole(@RequestBody RolDto rolDto) {
        logger.info("Creating a new role: {}", rolDto.getName());
        try {
            RolDto createdRol = rolBl.createRol(rolDto);
            ResponseDto<RolDto> response = ResponseDto.success(createdRol, "Role created successfully");
            logger.info("Role created successfully: {}", createdRol.getName());
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("Error creating role: {}", e.getMessage());
            ResponseDto<RolDto> response = ResponseDto.error("Error creating role", HttpStatus.INTERNAL_SERVER_ERROR.value());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Obtener todos los roles
    @GetMapping
    public ResponseEntity<ResponseDto<List<RolDto>>> getAllRoles() {
        logger.info("Fetching all roles");
        List<RolDto> roles = rolBl.getAllRoles();
        ResponseDto<List<RolDto>> response = ResponseDto.success(roles, "Roles fetched successfully");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Obtener un rol por su ID
    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto<RolDto>> getRoleById(@PathVariable("id") Integer id) {
        logger.info("Fetching role with ID: {}", id);
        Optional<RolDto> rolDto = rolBl.getRolById(id);
        if (rolDto.isPresent()) {
            ResponseDto<RolDto> response = ResponseDto.success(rolDto.get(), "Role found");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            logger.warn("Role with ID: {} not found", id);
            ResponseDto<RolDto> response = ResponseDto.error("Role not found", HttpStatus.NOT_FOUND.value());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    // Actualizar un rol
    @PutMapping("/{id}")
    public ResponseEntity<ResponseDto<RolDto>> updateRole(@PathVariable("id") Integer id, @RequestBody RolDto rolDto) {
        logger.info("Updating role with ID: {}", id);
        Optional<RolDto> updatedRol = rolBl.updateRol(id, rolDto);
        if (updatedRol.isPresent()) {
            ResponseDto<RolDto> response = ResponseDto.success(updatedRol.get(), "Role updated successfully");
            logger.info("Role updated successfully: {}", updatedRol.get().getName());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            logger.warn("Role with ID: {} not found for update", id);
            ResponseDto<RolDto> response = ResponseDto.error("Role not found for update", HttpStatus.NOT_FOUND.value());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    // Eliminar un rol
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto<Void>> deleteRole(@PathVariable("id") Integer id) {
        logger.info("Deleting role with ID: {}", id);
        boolean deleted = rolBl.deleteRol(id);
        if (deleted) {
            ResponseDto<Void> response = ResponseDto.success(null, "Role deleted successfully");
            logger.info("Role with ID: {} deleted successfully", id);
            return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
        } else {
            logger.warn("Role with ID: {} not found for deletion", id);
            ResponseDto<Void> response = ResponseDto.error("Role not found for deletion", HttpStatus.NOT_FOUND.value());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }
}
