package com.project.pet_veteriana.controller;

import com.project.pet_veteriana.bl.ReviewsBl;
import com.project.pet_veteriana.config.JwtTokenProvider;
import com.project.pet_veteriana.dto.ReviewsDto;
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
@RequestMapping("/api/reviews")
public class ReviewsController {

    private static final Logger logger = LoggerFactory.getLogger(ReviewsController.class);

    @Autowired
    private ReviewsBl reviewsBl;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @PostMapping
    public ResponseEntity<ResponseDto<ReviewsDto>> createReview(HttpServletRequest request, @RequestBody ReviewsDto dto) {
        String token = request.getHeader("Authorization");
        String extractedToken = token.replace("Bearer ", "");
        String username = jwtTokenProvider.extractUsername(extractedToken);

        if (username == null || !jwtTokenProvider.validateToken(extractedToken, username)) {
            logger.error("Unauthorized access attempt with token: {}", token);
            return new ResponseEntity<>(ResponseDto.error("Unauthorized", 401), HttpStatus.UNAUTHORIZED);
        }

        logger.info("Creating a new review by user: {}", username);
        ReviewsDto createdReview = reviewsBl.createReview(dto);
        return new ResponseEntity<>(ResponseDto.success(createdReview, "Review created successfully"), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<ResponseDto<List<ReviewsDto>>> getAllReviews(@RequestHeader("Authorization") String token) {
        String extractedToken = token.replace("Bearer ", "");
        String username = jwtTokenProvider.extractUsername(extractedToken);

        if (username == null || !jwtTokenProvider.validateToken(extractedToken, username)) {
            logger.error("Unauthorized access attempt with token: {}", token);
            return new ResponseEntity<>(ResponseDto.error("Unauthorized", 401), HttpStatus.UNAUTHORIZED);
        }

        logger.info("Fetching all reviews by user: {}", username);
        List<ReviewsDto> reviews = reviewsBl.getAllReviews();
        return new ResponseEntity<>(ResponseDto.success(reviews, "Reviews fetched successfully"), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto<ReviewsDto>> getReviewById(@PathVariable Integer id, @RequestHeader("Authorization") String token) {
        String extractedToken = token.replace("Bearer ", "");
        String username = jwtTokenProvider.extractUsername(extractedToken);

        if (username == null || !jwtTokenProvider.validateToken(extractedToken, username)) {
            logger.error("Unauthorized access attempt with token: {}", token);
            return new ResponseEntity<>(ResponseDto.error("Unauthorized", 401), HttpStatus.UNAUTHORIZED);
        }

        logger.info("Fetching review with ID: {} by user: {}", id, username);
        ReviewsDto review = reviewsBl.getReviewById(id);
        return new ResponseEntity<>(ResponseDto.success(review, "Review fetched successfully"), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDto<ReviewsDto>> updateReview(@PathVariable Integer id, @RequestBody ReviewsDto dto, @RequestHeader("Authorization") String token) {
        String extractedToken = token.replace("Bearer ", "");
        String username = jwtTokenProvider.extractUsername(extractedToken);

        if (username == null || !jwtTokenProvider.validateToken(extractedToken, username)) {
            logger.error("Unauthorized access attempt with token: {}", token);
            return new ResponseEntity<>(ResponseDto.error("Unauthorized", 401), HttpStatus.UNAUTHORIZED);
        }

        logger.info("Updating review with ID: {} by user: {}", id, username);
        ReviewsDto updatedReview = reviewsBl.updateReview(id, dto);
        return new ResponseEntity<>(ResponseDto.success(updatedReview, "Review updated successfully"), HttpStatus.OK);
    }

    @GetMapping("/provider/{providerId}")
    public ResponseEntity<ResponseDto<List<ReviewsDto>>> getReviewsByProviderId(
            @PathVariable Integer providerId,
            @RequestHeader("Authorization") String token) {

        String extractedToken = token.replace("Bearer ", "");
        String username = jwtTokenProvider.extractUsername(extractedToken);

        if (username == null || !jwtTokenProvider.validateToken(extractedToken, username)) {
            logger.error("Unauthorized access attempt with token: {}", token);
            return new ResponseEntity<>(ResponseDto.error("Unauthorized", 401), HttpStatus.UNAUTHORIZED);
        }

        logger.info("Fetching reviews for provider ID: {} by user: {}", providerId, username);
        List<ReviewsDto> reviews = reviewsBl.getReviewsByProviderId(providerId);
        return new ResponseEntity<>(ResponseDto.success(reviews, "Reviews fetched successfully"), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto<Void>> deleteReview(@PathVariable Integer id, @RequestHeader("Authorization") String token) {
        String extractedToken = token.replace("Bearer ", "");
        String username = jwtTokenProvider.extractUsername(extractedToken);

        if (username == null || !jwtTokenProvider.validateToken(extractedToken, username)) {
            logger.error("Unauthorized access attempt with token: {}", token);
            return new ResponseEntity<>(ResponseDto.error("Unauthorized", 401), HttpStatus.UNAUTHORIZED);
        }

        logger.info("Deleting review with ID: {} by user: {}", id, username);
        boolean deleted = reviewsBl.deleteReview(id);
        if (deleted) {
            return new ResponseEntity<>(ResponseDto.success(null, "Review deleted successfully"), HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(ResponseDto.error("Review not found", HttpStatus.NOT_FOUND.value()), HttpStatus.NOT_FOUND);
        }
    }
}
