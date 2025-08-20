package com.project.pet_veteriana.repository;

import com.project.pet_veteriana.entity.Products;
import com.project.pet_veteriana.entity.Providers;
import com.project.pet_veteriana.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductsRepository extends JpaRepository<Products, Integer> {
    List<Products> findTop10ByOrderByCreatedAtDesc();
    List<Products> findByProvider(Providers provider);
    int countByProvider(Providers provider);
    List<Products> findByCategory(Category category);
    @Modifying
    @Query("UPDATE Products p SET p.image = NULL WHERE p.image.imageId = :imageId")
    void setImageNullForProducts(@Param("imageId") Integer imageId);

}
