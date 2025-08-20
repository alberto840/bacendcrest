package com.project.pet_veteriana.repository;

import com.project.pet_veteriana.entity.Services;
import com.project.pet_veteriana.entity.TransactionHistory;
import com.project.pet_veteriana.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface TransactionHistoryRepository extends JpaRepository<TransactionHistory, Integer> {

    void deleteByService(Services service);

    // Buscar transacciones por User ID
    List<TransactionHistory> findByUserUserId(Integer userId);

    // Buscar transacciones por Provider ID (Productos o Servicios)
    @Query("SELECT t FROM TransactionHistory t " +
            "LEFT JOIN t.product p " +
            "LEFT JOIN t.service s " +
            "WHERE (p.provider.providerId = :providerId OR s.provider.providerId = :providerId)")
    List<TransactionHistory> findByProviderId(Integer providerId);

    @Modifying
    @Transactional
    @Query("DELETE FROM TransactionHistory t WHERE t.user = :user")
    void deleteByUser(Users user);
}
