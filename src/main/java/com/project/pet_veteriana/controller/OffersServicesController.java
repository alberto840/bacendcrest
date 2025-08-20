// OffersServicesController.java
package com.project.pet_veteriana.controller;

import com.project.pet_veteriana.bl.OffersServicesBl;
import com.project.pet_veteriana.config.JwtTokenProvider;
import com.project.pet_veteriana.dto.OffersServicesDto;
import com.project.pet_veteriana.dto.ResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/offers-services")
public class OffersServicesController {

    private static final Logger logger = LoggerFactory.getLogger(OffersServicesController.class);

    @Autowired
    private OffersServicesBl offersServicesBl;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @PostMapping
    public ResponseEntity<ResponseDto<OffersServicesDto>> createOfferService(HttpServletRequest request, @RequestBody OffersServicesDto dto) {
        String token = request.getHeader("Authorization");
        String extractedToken = token.replace("Bearer ", "");
        String username = jwtTokenProvider.extractUsername(extractedToken);

        if (username == null || !jwtTokenProvider.validateToken(extractedToken, username)) {
            logger.error("Unauthorized access attempt with token: {}", token);
            return new ResponseEntity<>(ResponseDto.error("Unauthorized", 401), HttpStatus.UNAUTHORIZED);
        }

        logger.info("Creating new Offer-Service relation by user: {}", username);
        OffersServicesDto createdRelation = offersServicesBl.createOfferService(dto);
        return new ResponseEntity<>(ResponseDto.success(createdRelation, "Offer-Service relation created successfully"), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<ResponseDto<List<OffersServicesDto>>> getAllOfferServices(@RequestHeader("Authorization") String token) {
        String extractedToken = token.replace("Bearer ", "");
        String username = jwtTokenProvider.extractUsername(extractedToken);

        if (username == null || !jwtTokenProvider.validateToken(extractedToken, username)) {
            logger.error("Unauthorized access attempt with token: {}", token);
            return new ResponseEntity<>(ResponseDto.error("Unauthorized", 401), HttpStatus.UNAUTHORIZED);
        }

        logger.info("Fetching all Offer-Service relations by user: {}", username);
        List<OffersServicesDto> relations = offersServicesBl.getAllOfferServices();
        return new ResponseEntity<>(ResponseDto.success(relations, "Offer-Service relations fetched successfully"), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto<OffersServicesDto>> getOfferServiceById(@PathVariable Integer id, @RequestHeader("Authorization") String token) {
        String extractedToken = token.replace("Bearer ", "");
        String username = jwtTokenProvider.extractUsername(extractedToken);

        if (username == null || !jwtTokenProvider.validateToken(extractedToken, username)) {
            logger.error("Unauthorized access attempt with token: {}", token);
            return new ResponseEntity<>(ResponseDto.error("Unauthorized", 401), HttpStatus.UNAUTHORIZED);
        }

        logger.info("Fetching Offer-Service relation with ID: {} by user: {}", id, username);
        OffersServicesDto relation = offersServicesBl.getOfferServiceById(id);
        return new ResponseEntity<>(ResponseDto.success(relation, "Offer-Service relation fetched successfully"), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDto<OffersServicesDto>> updateOfferService(@PathVariable Integer id, @RequestBody OffersServicesDto dto, @RequestHeader("Authorization") String token) {
        String extractedToken = token.replace("Bearer ", "");
        String username = jwtTokenProvider.extractUsername(extractedToken);

        if (username == null || !jwtTokenProvider.validateToken(extractedToken, username)) {
            logger.error("Unauthorized access attempt with token: {}", token);
            return new ResponseEntity<>(ResponseDto.error("Unauthorized", 401), HttpStatus.UNAUTHORIZED);
        }

        logger.info("Updating Offer-Service relation with ID: {} by user: {}", id, username);
        OffersServicesDto updatedRelation = offersServicesBl.updateOfferService(id, dto);
        return new ResponseEntity<>(ResponseDto.success(updatedRelation, "Offer-Service relation updated successfully"), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto<Void>> deleteOfferService(@PathVariable Integer id, @RequestHeader("Authorization") String token) {
        String extractedToken = token.replace("Bearer ", "");
        String username = jwtTokenProvider.extractUsername(extractedToken);

        if (username == null || !jwtTokenProvider.validateToken(extractedToken, username)) {
            logger.error("Unauthorized access attempt with token: {}", token);
            return new ResponseEntity<>(ResponseDto.error("Unauthorized", 401), HttpStatus.UNAUTHORIZED);
        }

        logger.info("Deleting Offer-Service relation with ID: {} by user: {}", id, username);
        boolean deleted = offersServicesBl.deleteOfferService(id);
        if (deleted) {
            return new ResponseEntity<>(ResponseDto.success(null, "Offer-Service relation deleted successfully"), HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(ResponseDto.error("Offer-Service relation not found", HttpStatus.NOT_FOUND.value()), HttpStatus.NOT_FOUND);
        }
    }
}
