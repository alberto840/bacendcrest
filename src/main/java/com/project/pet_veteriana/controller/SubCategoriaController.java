package com.project.pet_veteriana.controller;

import com.project.pet_veteriana.bl.SubCategoriaService;
import com.project.pet_veteriana.dto.SubCategoriaDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subcategorias")
public class SubCategoriaController {

    @Autowired
    private SubCategoriaService subCategoriaService;

    // Crear una nueva SubCategoria
    @PostMapping
    public ResponseEntity<SubCategoriaDto> createSubCategoria(@RequestBody SubCategoriaDto dto) {
        SubCategoriaDto createdSubCategoria = subCategoriaService.createSubCategoria(dto);
        return ResponseEntity.ok(createdSubCategoria);
    }

    // Obtener todas las SubCategorias
    @GetMapping
    public ResponseEntity<List<SubCategoriaDto>> getAllSubCategorias() {
        List<SubCategoriaDto> subCategorias = subCategoriaService.getAllSubCategorias();
        return ResponseEntity.ok(subCategorias);
    }

    // Obtener una SubCategoria por ID
    @GetMapping("/{id}")
    public ResponseEntity<SubCategoriaDto> getSubCategoriaById(@PathVariable Integer id) {
        SubCategoriaDto subCategoria = subCategoriaService.getSubCategoriaById(id);
        if (subCategoria != null) {
            return ResponseEntity.ok(subCategoria);
        }
        return ResponseEntity.notFound().build();
    }

    // Actualizar una SubCategoria
    @PutMapping("/{id}")
    public ResponseEntity<SubCategoriaDto> updateSubCategoria(@PathVariable Integer id, @RequestBody SubCategoriaDto dto) {
        try {
            SubCategoriaDto updatedSubCategoria = subCategoriaService.updateSubCategoria(id, dto);
            return ResponseEntity.ok(updatedSubCategoria);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    // Eliminar una SubCategoria
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSubCategoria(@PathVariable Integer id) {
        if (subCategoriaService.deleteSubCategoria(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
