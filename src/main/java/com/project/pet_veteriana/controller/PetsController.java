package com.project.pet_veteriana.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.pet_veteriana.bl.PetsBl;
import com.project.pet_veteriana.config.JwtTokenProvider;
import com.project.pet_veteriana.dto.PetsDto;
import com.project.pet_veteriana.dto.ResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/pets")
public class PetsController {

    private static final Logger logger = LoggerFactory.getLogger(PetsController.class);

    @Autowired
    private PetsBl petsBl;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @PostMapping
    public ResponseEntity<ResponseDto<PetsDto>> createPetWithImage(
            HttpServletRequest request,
            @RequestParam("pet") String petDtoJson,
            @RequestParam("file") MultipartFile file) throws Exception {

        String token = request.getHeader("Authorization");
        String extractedToken = token.replace("Bearer ", "");
        String username = jwtTokenProvider.extractUsername(extractedToken);

        if (username == null || !jwtTokenProvider.validateToken(extractedToken, username)) {
            logger.error("Unauthorized access attempt.");
            return new ResponseEntity<>(ResponseDto.error("Unauthorized", 401), HttpStatus.UNAUTHORIZED);
        }

        logger.info("Creating new pet with image");

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.findAndRegisterModules(); // 🔥 permite reconocer LocalDate, etc.
            PetsDto petsDto = objectMapper.readValue(petDtoJson, PetsDto.class);

            PetsDto createdPet = petsBl.createPetWithImage(petsDto, file);
            ResponseDto<PetsDto> response = ResponseDto.success(createdPet, "Pet created successfully");
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("Error creating pet: {}", e.getMessage(), e);
            ResponseDto<PetsDto> response = ResponseDto.error("Error creating pet", HttpStatus.INTERNAL_SERVER_ERROR.value());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping
    public ResponseEntity<ResponseDto<List<PetsDto>>> getAllPets(@RequestHeader("Authorization") String token) {
        String extractedToken = token.replace("Bearer ", "");
        String username = jwtTokenProvider.extractUsername(extractedToken);

        if (username == null || !jwtTokenProvider.validateToken(extractedToken, username)) {
            logger.error("Unauthorized access attempt.");
            return new ResponseEntity<>(ResponseDto.error("Unauthorized", 401), HttpStatus.UNAUTHORIZED);
        }

        logger.info("Fetching all pets");
        List<PetsDto> pets = petsBl.getAllPets();
        ResponseDto<List<PetsDto>> response = ResponseDto.success(pets, "Pets fetched successfully");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto<PetsDto>> getPetById(
            @PathVariable("id") Integer id,
            @RequestHeader("Authorization") String token) {

        String extractedToken = token.replace("Bearer ", "");
        String username = jwtTokenProvider.extractUsername(extractedToken);

        if (username == null || !jwtTokenProvider.validateToken(extractedToken, username)) {
            logger.error("Unauthorized access attempt.");
            return new ResponseEntity<>(ResponseDto.error("Unauthorized", 401), HttpStatus.UNAUTHORIZED);
        }

        logger.info("Fetching pet with ID: {}", id);
        Optional<PetsDto> pet = petsBl.getPetById(id);
        if (pet.isPresent()) {
            ResponseDto<PetsDto> response = ResponseDto.success(pet.get(), "Pet found");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            ResponseDto<PetsDto> response = ResponseDto.error("Pet not found", HttpStatus.NOT_FOUND.value());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDto<PetsDto>> updatePetWithImage(
            @PathVariable("id") Integer id,
            @RequestParam("pet") String petDtoJson,
            @RequestParam(value = "file", required = false) MultipartFile file,
            @RequestHeader("Authorization") String token) throws Exception {

        String extractedToken = token.replace("Bearer ", "");
        String username = jwtTokenProvider.extractUsername(extractedToken);

        if (username == null || !jwtTokenProvider.validateToken(extractedToken, username)) {
            logger.error("Unauthorized access attempt.");
            return new ResponseEntity<>(ResponseDto.error("Unauthorized", 401), HttpStatus.UNAUTHORIZED);
        }

        logger.info("Updating pet with ID: {}", id);
        ObjectMapper objectMapper = new ObjectMapper();
        PetsDto petsDto = objectMapper.readValue(petDtoJson, PetsDto.class);

        Optional<PetsDto> updatedPet = petsBl.updatePetWithImage(id, petsDto, file);
        if (updatedPet.isPresent()) {
            ResponseDto<PetsDto> response = ResponseDto.success(updatedPet.get(), "Pet updated successfully");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            ResponseDto<PetsDto> response = ResponseDto.error("Pet not found for update", HttpStatus.NOT_FOUND.value());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/by-user/{userId}")
    public ResponseEntity<ResponseDto<List<PetsDto>>> getPetsByUserId(
            @PathVariable Integer userId,
            @RequestHeader("Authorization") String token) {

        String extractedToken = token.replace("Bearer ", "");
        String username = jwtTokenProvider.extractUsername(extractedToken);

        if (username == null || !jwtTokenProvider.validateToken(extractedToken, username)) {
            return new ResponseEntity<>(ResponseDto.error("Unauthorized", 401), HttpStatus.UNAUTHORIZED);
        }

        try {
            List<PetsDto> pets = petsBl.getPetsByUserId(userId);
            return new ResponseEntity<>(ResponseDto.success(pets, "Pets by user fetched successfully"), HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(ResponseDto.error(e.getMessage(), HttpStatus.NOT_FOUND.value()), HttpStatus.NOT_FOUND);
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto<Void>> deletePet(
            @PathVariable("id") Integer id,
            @RequestHeader("Authorization") String token) {

        String extractedToken = token.replace("Bearer ", "");
        String username = jwtTokenProvider.extractUsername(extractedToken);

        if (username == null || !jwtTokenProvider.validateToken(extractedToken, username)) {
            logger.error("Unauthorized access attempt.");
            return new ResponseEntity<>(ResponseDto.error("Unauthorized", 401), HttpStatus.UNAUTHORIZED);
        }

        logger.info("Deleting pet with ID: {}", id);
        boolean deleted = petsBl.deletePet(id);
        if (deleted) {
            ResponseDto<Void> response = ResponseDto.success(null, "Pet deleted successfully");
            return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
        } else {
            ResponseDto<Void> response = ResponseDto.error("Pet not found for deletion", HttpStatus.NOT_FOUND.value());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }
}
