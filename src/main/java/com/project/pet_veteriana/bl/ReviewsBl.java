package com.project.pet_veteriana.bl;

import com.project.pet_veteriana.dto.ReviewsDto;
import com.project.pet_veteriana.entity.Reviews;
import com.project.pet_veteriana.entity.Users;
import com.project.pet_veteriana.entity.Providers;
import com.project.pet_veteriana.repository.ReviewsRepository;
import com.project.pet_veteriana.repository.UsersRepository;
import com.project.pet_veteriana.repository.ProvidersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReviewsBl {

    @Autowired
    private ReviewsRepository reviewsRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private ProvidersRepository providersRepository;

    @Transactional
    public ReviewsDto createReview(ReviewsDto dto) {
        Optional<Users> userOptional = usersRepository.findById(dto.getUserId());
        Optional<Providers> providerOptional = providersRepository.findById(dto.getProviderId());

        if (userOptional.isEmpty()) {
            throw new IllegalArgumentException("User not found");
        }
        if (providerOptional.isEmpty()) {
            throw new IllegalArgumentException("Provider not found");
        }

        Reviews review = new Reviews();
        review.setRating(dto.getRating());
        review.setComment(dto.getComment());
        review.setCreatedAt(LocalDateTime.now());
        review.setUser(userOptional.get());
        review.setProvider(providerOptional.get());

        Reviews savedReview = reviewsRepository.save(review);
        return convertToDto(savedReview);
    }

    public List<ReviewsDto> getAllReviews() {
        return reviewsRepository.findAll()
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public ReviewsDto getReviewById(Integer id) {
        Reviews review = reviewsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Review not found"));
        return convertToDto(review);
    }

    public List<ReviewsDto> getReviewsByProviderId(Integer providerId) {
        List<Reviews> reviews = reviewsRepository.findByProviderProviderId(providerId);
        return reviews.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @Transactional
    public ReviewsDto updateReview(Integer id, ReviewsDto dto) {
        Reviews existingReview = reviewsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Review not found"));

        existingReview.setRating(dto.getRating());
        existingReview.setComment(dto.getComment());
        Reviews updatedReview = reviewsRepository.save(existingReview);

        return convertToDto(updatedReview);
    }

    @Transactional
    public boolean deleteReview(Integer id) {
        if (reviewsRepository.existsById(id)) {
            reviewsRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private ReviewsDto convertToDto(Reviews review) {
        return new ReviewsDto(
                review.getReviewsId(),
                review.getRating(),
                review.getComment(),
                review.getCreatedAt(),
                review.getUser().getUserId(),
                review.getProvider().getProviderId()
        );
    }
}
