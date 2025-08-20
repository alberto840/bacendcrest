package com.project.pet_veteriana.controller;

import com.project.pet_veteriana.bl.CategoryBl;
import com.project.pet_veteriana.dto.CategoryDto;
import com.project.pet_veteriana.dto.ResponseDto;
import com.project.pet_veteriana.config.JwtTokenProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private static final Logger logger = LoggerFactory.getLogger(CategoryController.class);

    @Autowired
    private CategoryBl categoryBl;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    // Crear una nueva categoría
    @PostMapping
    public ResponseEntity<ResponseDto<CategoryDto>> createCategory(HttpServletRequest request, @RequestBody CategoryDto categoryDto) {
        String token = request.getHeader("Authorization");
        logger.info("Solicitud para crear categoría: {}", categoryDto.getNameCategory());

        // Validar el token JWT antes de proceder
        String extractedToken = token.replace("Bearer ", "");
        String username = jwtTokenProvider.extractUsername(extractedToken);

        if (username == null || !jwtTokenProvider.validateToken(extractedToken, username)) {
            logger.error("Acceso no autorizado con el token: {}", token);
            return new ResponseEntity<>(ResponseDto.error("Unauthorized", 401), HttpStatus.UNAUTHORIZED);
        }

        CategoryDto createdCategory = categoryBl.createCategory(categoryDto);
        if (createdCategory != null) {
            // Cambié el tipo del ResponseDto a ResponseDto<CategoryDto> y usé la respuesta de éxito
            ResponseDto<CategoryDto> response = ResponseDto.success(createdCategory, "Categoría creada exitosamente");
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } else {
            // Cambié el tipo del ResponseDto a ResponseDto<CategoryDto> y usé la respuesta de error
            ResponseDto<CategoryDto> response = ResponseDto.error("Error al crear la categoría", HttpStatus.BAD_REQUEST.value());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    // Obtener todas las categorías
    @GetMapping
    public ResponseEntity<ResponseDto<List<CategoryDto>>> getAllCategories(@RequestHeader("Authorization") String token) {
        logger.info("Solicitud para obtener todas las categorías");

        // Validar el token JWT antes de proceder
        String extractedToken = token.replace("Bearer ", "");
        String username = jwtTokenProvider.extractUsername(extractedToken);

        if (username == null || !jwtTokenProvider.validateToken(extractedToken, username)) {
            logger.error("Acceso no autorizado con el token: {}", token);
            return new ResponseEntity<>(ResponseDto.error("Unauthorized", 401), HttpStatus.UNAUTHORIZED);
        }

        List<CategoryDto> categories = categoryBl.getAllCategories();
        if (!categories.isEmpty()) {
            // Cambié el tipo del ResponseDto a ResponseDto<List<CategoryDto>> y usé la respuesta de éxito
            ResponseDto<List<CategoryDto>> response = ResponseDto.success(categories, "Categorías obtenidas exitosamente");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            // Cambié el tipo del ResponseDto a ResponseDto<List<CategoryDto>> y usé la respuesta de error
            ResponseDto<List<CategoryDto>> response = ResponseDto.error("No se encontraron categorías", HttpStatus.NOT_FOUND.value());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    // Obtener categoría por ID
    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto<CategoryDto>> getCategoryById(@PathVariable Integer id, @RequestHeader("Authorization") String token) {
        logger.info("Solicitud para obtener categoría con ID: {}", id);

        // Validar el token JWT antes de proceder
        String extractedToken = token.replace("Bearer ", "");
        String username = jwtTokenProvider.extractUsername(extractedToken);

        if (username == null || !jwtTokenProvider.validateToken(extractedToken, username)) {
            logger.error("Acceso no autorizado con el token: {}", token);
            return new ResponseEntity<>(ResponseDto.error("Unauthorized", 401), HttpStatus.UNAUTHORIZED);
        }

        CategoryDto category = categoryBl.getCategoryById(id);
        if (category != null) {
            // Cambié el tipo del ResponseDto a ResponseDto<CategoryDto> y usé la respuesta de éxito
            ResponseDto<CategoryDto> response = ResponseDto.success(category, "Categoría obtenida exitosamente");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            // Cambié el tipo del ResponseDto a ResponseDto<CategoryDto> y usé la respuesta de error
            ResponseDto<CategoryDto> response = ResponseDto.error("Categoría no encontrada", HttpStatus.NOT_FOUND.value());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    // Actualizar categoría
    @PutMapping("/{id}")
    public ResponseEntity<ResponseDto<CategoryDto>> updateCategory(@PathVariable Integer id, @RequestBody CategoryDto categoryDto, @RequestHeader("Authorization") String token) {
        logger.info("Solicitud para actualizar categoría con ID: {}", id);

        // Validar el token JWT antes de proceder
        String extractedToken = token.replace("Bearer ", "");
        String username = jwtTokenProvider.extractUsername(extractedToken);

        if (username == null || !jwtTokenProvider.validateToken(extractedToken, username)) {
            logger.error("Acceso no autorizado con el token: {}", token);
            return new ResponseEntity<>(ResponseDto.error("Unauthorized", 401), HttpStatus.UNAUTHORIZED);
        }

        CategoryDto updatedCategory = categoryBl.updateCategory(id, categoryDto);
        if (updatedCategory != null) {
            // Cambié el tipo del ResponseDto a ResponseDto<CategoryDto> y usé la respuesta de éxito
            ResponseDto<CategoryDto> response = ResponseDto.success(updatedCategory, "Categoría actualizada exitosamente");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            // Cambié el tipo del ResponseDto a ResponseDto<CategoryDto> y usé la respuesta de error
            ResponseDto<CategoryDto> response = ResponseDto.error("Categoría no encontrada", HttpStatus.NOT_FOUND.value());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    // Eliminar categoría
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto<Void>> deleteCategory(@PathVariable Integer id, @RequestHeader("Authorization") String token) {
        logger.info("Solicitud para eliminar categoría con ID: {}", id);

        // Validar el token JWT antes de proceder
        String extractedToken = token.replace("Bearer ", "");
        String username = jwtTokenProvider.extractUsername(extractedToken);

        if (username == null || !jwtTokenProvider.validateToken(extractedToken, username)) {
            logger.error("Acceso no autorizado con el token: {}", token);
            return new ResponseEntity<>(ResponseDto.error("Unauthorized", 401), HttpStatus.UNAUTHORIZED);
        }

        boolean isDeleted = categoryBl.deleteCategory(id);
        if (isDeleted) {
            // Cambié el tipo del ResponseDto a ResponseDto<Void> y usé la respuesta de éxito
            ResponseDto<Void> response = ResponseDto.success(null, "Categoría eliminada exitosamente");
            return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
        } else {
            // Cambié el tipo del ResponseDto a ResponseDto<Void> y usé la respuesta de error
            ResponseDto<Void> response = ResponseDto.error("Categoría no encontrada", HttpStatus.NOT_FOUND.value());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }
}
