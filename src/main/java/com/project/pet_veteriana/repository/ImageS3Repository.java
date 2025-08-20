package com.project.pet_veteriana.repository;

import com.project.pet_veteriana.entity.ImageS3;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ImageS3Repository extends JpaRepository<ImageS3, Integer> {

    Optional<ImageS3> findByFileName(String fileName);

    Optional<ImageS3> findById(Integer imageId);


}
