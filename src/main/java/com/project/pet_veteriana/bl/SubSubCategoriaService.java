package com.project.pet_veteriana.bl;

import com.project.pet_veteriana.dto.SubSubCategoriaDto;
import com.project.pet_veteriana.entity.SubCategoria;
import com.project.pet_veteriana.entity.SubSubCategoria;
import com.project.pet_veteriana.repository.SubCategoriaRepository;
import com.project.pet_veteriana.repository.SubSubCategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SubSubCategoriaService {

    @Autowired
    private SubSubCategoriaRepository subSubCategoriaRepository;

    @Autowired
    private SubCategoriaRepository subCategoriaRepository;

    // Crear una nueva SubSubCategoria
    @Transactional
    public SubSubCategoriaDto createSubSubCategoria(SubSubCategoriaDto dto) {
        Optional<SubCategoria> subCategoriaOpt = subCategoriaRepository.findById(dto.getSubCategoriaId());
        if (subCategoriaOpt.isPresent()) {
            SubSubCategoria subSubCategoria = new SubSubCategoria();
            subSubCategoria.setNameSubSubCategoria(dto.getNameSubSubCategoria());
            subSubCategoria.setSubCategoria(subCategoriaOpt.get());

            SubSubCategoria savedSubSubCategoria = subSubCategoriaRepository.save(subSubCategoria);
            return convertToDto(savedSubSubCategoria);
        }
        throw new IllegalArgumentException("SubCategoria con ID " + dto.getSubCategoriaId() + " no existe");
    }

    // Obtener todas las SubSubCategorias
    public List<SubSubCategoriaDto> getAllSubSubCategorias() {
        return subSubCategoriaRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    // Obtener una SubSubCategoria por ID
    public SubSubCategoriaDto getSubSubCategoriaById(Integer id) {
        Optional<SubSubCategoria> subSubCategoria = subSubCategoriaRepository.findById(id);
        return subSubCategoria.map(this::convertToDto).orElse(null);
    }

    // Actualizar una SubSubCategoria
    @Transactional
    public SubSubCategoriaDto updateSubSubCategoria(Integer id, SubSubCategoriaDto dto) {
        Optional<SubSubCategoria> subSubCategoriaOpt = subSubCategoriaRepository.findById(id);
        if (subSubCategoriaOpt.isPresent()) {
            SubSubCategoria subSubCategoria = subSubCategoriaOpt.get();
            subSubCategoria.setNameSubSubCategoria(dto.getNameSubSubCategoria());

            Optional<SubCategoria> subCategoriaOpt = subCategoriaRepository.findById(dto.getSubCategoriaId());
            if (subCategoriaOpt.isPresent()) {
                subSubCategoria.setSubCategoria(subCategoriaOpt.get());
            } else {
                throw new IllegalArgumentException("SubCategoria con ID " + dto.getSubCategoriaId() + " no existe");
            }

            SubSubCategoria updatedSubSubCategoria = subSubCategoriaRepository.save(subSubCategoria);
            return convertToDto(updatedSubSubCategoria);
        }
        throw new IllegalArgumentException("SubSubCategoria con ID " + id + " no existe");
    }

    // Eliminar una SubSubCategoria
    @Transactional
    public boolean deleteSubSubCategoria(Integer id) {
        if (subSubCategoriaRepository.existsById(id)) {
            subSubCategoriaRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Convertir entidad a DTO
    private SubSubCategoriaDto convertToDto(SubSubCategoria subSubCategoria) {
        return new SubSubCategoriaDto(
                subSubCategoria.getSubSubCategoriaId(),
                subSubCategoria.getNameSubSubCategoria(),
                subSubCategoria.getSubCategoria().getSubCategoriaId()
        );
    }
}
