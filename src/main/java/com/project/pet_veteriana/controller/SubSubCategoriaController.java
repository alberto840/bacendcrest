package com.project.pet_veteriana.controller;

import com.project.pet_veteriana.bl.SubSubCategoriaService;
import com.project.pet_veteriana.dto.SubSubCategoriaDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subsubcategorias")
public class SubSubCategoriaController {

    @Autowired
    private SubSubCategoriaService subSubCategoriaService;

    // Crear una nueva SubSubCategoria
    @PostMapping
    public ResponseEntity<SubSubCategoriaDto> createSubSubCategoria(@RequestBody SubSubCategoriaDto dto) {
        SubSubCategoriaDto createdSubSubCategoria = subSubCategoriaService.createSubSubCategoria(dto);
        return ResponseEntity.ok(createdSubSubCategoria);
    }

    // Obtener todas las SubSubCategorias
    @GetMapping
    public ResponseEntity<List<SubSubCategoriaDto>> getAllSubSubCategorias() {
        List<SubSubCategoriaDto> subSubCategorias = subSubCategoriaService.getAllSubSubCategorias();
        return ResponseEntity.ok(subSubCategorias);
    }

    // Obtener una SubSubCategoria por ID
    @GetMapping("/{id}")
    public ResponseEntity<SubSubCategoriaDto> getSubSubCategoriaById(@PathVariable Integer id) {
        SubSubCategoriaDto subSubCategoria = subSubCategoriaService.getSubSubCategoriaById(id);
        if (subSubCategoria != null) {
            return ResponseEntity.ok(subSubCategoria);
        }
        return ResponseEntity.notFound().build();
    }

    // Actualizar una SubSubCategoria
    @PutMapping("/{id}")
    public ResponseEntity<SubSubCategoriaDto> updateSubSubCategoria(@PathVariable Integer id, @RequestBody SubSubCategoriaDto dto) {
        try {
            SubSubCategoriaDto updatedSubSubCategoria = subSubCategoriaService.updateSubSubCategoria(id, dto);
            return ResponseEntity.ok(updatedSubSubCategoria);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    // Eliminar una SubSubCategoria
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSubSubCategoria(@PathVariable Integer id) {
        if (subSubCategoriaService.deleteSubSubCategoria(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
