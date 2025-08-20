package com.project.pet_veteriana.repository;

import com.project.pet_veteriana.entity.Reviews;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewsRepository extends JpaRepository<Reviews, Integer> {

    List<Reviews> findByProviderProviderId(Integer providerId);
}
