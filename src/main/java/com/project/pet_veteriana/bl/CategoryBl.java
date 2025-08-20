package com.project.pet_veteriana.bl;

import com.project.pet_veteriana.dto.CategoryDto;
import com.project.pet_veteriana.entity.Category;
import com.project.pet_veteriana.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryBl {

    @Autowired
    private CategoryRepository categoryRepository;

    @Transactional
    public CategoryDto createCategory(CategoryDto categoryDto) {
        Category category = new Category();
        category.setNameCategory(categoryDto.getNameCategory());
        category.setCreatedAt(LocalDateTime.now());
        category.setIcono(categoryDto.getIcono()); // Setear el icono

        Category savedCategory = categoryRepository.save(category);
        return convertToDto(savedCategory);
    }

    public List<CategoryDto> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public CategoryDto getCategoryById(Integer id) {
        Optional<Category> category = categoryRepository.findById(id);
        return category.map(this::convertToDto).orElse(null);
    }

    @Transactional
    public CategoryDto updateCategory(Integer id, CategoryDto categoryDto) {
        Optional<Category> categoryOptional = categoryRepository.findById(id);
        if (categoryOptional.isPresent()) {
            Category category = categoryOptional.get();
            category.setNameCategory(categoryDto.getNameCategory());
            category.setCreatedAt(LocalDateTime.now());
            category.setIcono(categoryDto.getIcono()); // Actualizar el icono

            Category updatedCategory = categoryRepository.save(category);
            return convertToDto(updatedCategory);
        }
        return null;
    }

    @Transactional
    public boolean deleteCategory(Integer id) {
        if (categoryRepository.existsById(id)) {
            categoryRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private CategoryDto convertToDto(Category category) {
        return new CategoryDto(
                category.getCategoryId(),
                category.getNameCategory(),
                category.getCreatedAt(),
                category.getIcono() // Convertir el icono
        );
    }
}
