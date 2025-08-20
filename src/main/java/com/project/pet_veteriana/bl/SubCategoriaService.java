package com.project.pet_veteriana.bl;

import com.project.pet_veteriana.dto.SubCategoriaDto;
import com.project.pet_veteriana.entity.Category;
import com.project.pet_veteriana.entity.SubCategoria;
import com.project.pet_veteriana.repository.CategoryRepository;
import com.project.pet_veteriana.repository.SubCategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SubCategoriaService {

    @Autowired
    private SubCategoriaRepository subCategoriaRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    // Crear una nueva SubCategoria
    @Transactional
    public SubCategoriaDto createSubCategoria(SubCategoriaDto dto) {
        Optional<Category> categoryOpt = categoryRepository.findById(dto.getCategoryId());
        if (categoryOpt.isPresent()) {
            SubCategoria subCategoria = new SubCategoria();
            subCategoria.setNameSubCategoria(dto.getNameSubCategoria());
            subCategoria.setCategory(categoryOpt.get());

            SubCategoria savedSubCategoria = subCategoriaRepository.save(subCategoria);
            return convertToDto(savedSubCategoria);
        }
        throw new IllegalArgumentException("Category con ID " + dto.getCategoryId() + " no existe");
    }

    // Obtener todas las SubCategorias
    public List<SubCategoriaDto> getAllSubCategorias() {
        return subCategoriaRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    // Obtener una SubCategoria por ID
    public SubCategoriaDto getSubCategoriaById(Integer id) {
        Optional<SubCategoria> subCategoria = subCategoriaRepository.findById(id);
        return subCategoria.map(this::convertToDto).orElse(null);
    }

    // Actualizar una SubCategoria
    @Transactional
    public SubCategoriaDto updateSubCategoria(Integer id, SubCategoriaDto dto) {
        Optional<SubCategoria> subCategoriaOpt = subCategoriaRepository.findById(id);
        if (subCategoriaOpt.isPresent()) {
            SubCategoria subCategoria = subCategoriaOpt.get();
            subCategoria.setNameSubCategoria(dto.getNameSubCategoria());

            Optional<Category> categoryOpt = categoryRepository.findById(dto.getCategoryId());
            if (categoryOpt.isPresent()) {
                subCategoria.setCategory(categoryOpt.get());
            } else {
                throw new IllegalArgumentException("Category con ID " + dto.getCategoryId() + " no existe");
            }

            SubCategoria updatedSubCategoria = subCategoriaRepository.save(subCategoria);
            return convertToDto(updatedSubCategoria);
        }
        throw new IllegalArgumentException("SubCategoria con ID " + id + " no existe");
    }

    // Eliminar una SubCategoria
    @Transactional
    public boolean deleteSubCategoria(Integer id) {
        if (subCategoriaRepository.existsById(id)) {
            subCategoriaRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Convertir entidad a DTO
    private SubCategoriaDto convertToDto(SubCategoria subCategoria) {
        return new SubCategoriaDto(
                subCategoria.getSubCategoriaId(),
                subCategoria.getNameSubCategoria(),
                subCategoria.getCategory().getCategoryId()
        );
    }
}
