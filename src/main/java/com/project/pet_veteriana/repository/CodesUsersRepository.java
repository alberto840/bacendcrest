package com.project.pet_veteriana.repository;

import com.project.pet_veteriana.entity.CodesUsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CodesUsersRepository extends JpaRepository<CodesUsersEntity, Integer> {

    List<CodesUsersEntity> findByUser_UserId(Integer userId);
    List<CodesUsersEntity> findByPromo_Provider_ProviderId(Integer providerId);

}
