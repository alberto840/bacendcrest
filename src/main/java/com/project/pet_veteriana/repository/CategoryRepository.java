package com.project.pet_veteriana.repository;

import com.project.pet_veteriana.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
