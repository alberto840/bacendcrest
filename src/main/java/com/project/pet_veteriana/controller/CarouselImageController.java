package com.project.pet_veteriana.controller;

import com.project.pet_veteriana.bl.CarouselImageBl;
import com.project.pet_veteriana.dto.CarouselImageDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/carousel")
public class CarouselImageController {

    @Autowired
    private CarouselImageBl carouselImageBl;

    // Subir nueva imagen
    @PostMapping("/upload")
    public ResponseEntity<CarouselImageDto> uploadImage(@RequestParam("file") MultipartFile file) {
        CarouselImageDto savedImage = carouselImageBl.saveCarouselImage(file);
        return ResponseEntity.ok(savedImage);
    }

    // Obtener todas las imágenes con URL
    @GetMapping("/all")
    public ResponseEntity<List<CarouselImageDto>> getAllImages() {
        List<CarouselImageDto> images = carouselImageBl.getAllCarouselImages();
        return ResponseEntity.ok(images);
    }
}
