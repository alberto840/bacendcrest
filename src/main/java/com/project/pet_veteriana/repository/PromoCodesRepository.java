package com.project.pet_veteriana.repository;

import com.project.pet_veteriana.entity.PromoCodes;
import com.project.pet_veteriana.entity.Providers;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PromoCodesRepository extends JpaRepository<PromoCodes, Integer> {


    List<PromoCodes> findByProvider(Providers provider);

}
