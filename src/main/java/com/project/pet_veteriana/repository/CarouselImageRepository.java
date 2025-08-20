package com.project.pet_veteriana.repository;

import com.project.pet_veteriana.entity.CarouselImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CarouselImageRepository extends JpaRepository<CarouselImage, Long> {
    List<CarouselImage> findAllByOrderByIdDesc(Pageable pageable);
}
