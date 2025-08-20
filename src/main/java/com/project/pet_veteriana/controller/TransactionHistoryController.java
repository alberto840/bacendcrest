package com.project.pet_veteriana.controller;

import com.project.pet_veteriana.bl.TransactionHistoryBl;
import com.project.pet_veteriana.config.JwtTokenProvider;
import com.project.pet_veteriana.dto.ResponseDto;
import com.project.pet_veteriana.dto.TransactionHistoryDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api/transaction-history")
public class TransactionHistoryController {

    private static final Logger logger = LoggerFactory.getLogger(TransactionHistoryController.class);

    @Autowired
    private TransactionHistoryBl transactionHistoryBl;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    // Crear una nueva transacción
    @PostMapping
    public ResponseEntity<ResponseDto<TransactionHistoryDto>> createTransactionHistory(
            HttpServletRequest request, @RequestBody TransactionHistoryDto dto) {
        String token = request.getHeader("Authorization").replace("Bearer ", "");
        String username = jwtTokenProvider.extractUsername(token);

        if (username == null || !jwtTokenProvider.validateToken(token, username)) {
            logger.error("Unauthorized access attempt with token: {}", token);
            return new ResponseEntity<>(ResponseDto.error("Unauthorized", 401), HttpStatus.UNAUTHORIZED);
        }

        logger.info("Creating new transaction for user: {}", username);
        TransactionHistoryDto createdTransaction = transactionHistoryBl.createTransactionHistory(dto);
        return new ResponseEntity<>(ResponseDto.success(createdTransaction, "Transaction created successfully"), HttpStatus.CREATED);
    }

    // Obtener todas las transacciones
    @GetMapping
    public ResponseEntity<ResponseDto<List<TransactionHistoryDto>>> getAllTransactionHistories(
            @RequestHeader("Authorization") String token) {
        String extractedToken = token.replace("Bearer ", "");
        String username = jwtTokenProvider.extractUsername(extractedToken);

        if (username == null || !jwtTokenProvider.validateToken(extractedToken, username)) {
            logger.error("Unauthorized access attempt with token: {}", token);
            return new ResponseEntity<>(ResponseDto.error("Unauthorized", 401), HttpStatus.UNAUTHORIZED);
        }

        logger.info("Fetching all transactions for user: {}", username);
        List<TransactionHistoryDto> transactions = transactionHistoryBl.getAllTransactionHistories();
        return new ResponseEntity<>(ResponseDto.success(transactions, "Transactions fetched successfully"), HttpStatus.OK);
    }

    // Obtener una transacción por ID
    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto<TransactionHistoryDto>> getTransactionHistoryById(
            @PathVariable Integer id, @RequestHeader("Authorization") String token) {
        String extractedToken = token.replace("Bearer ", "");
        String username = jwtTokenProvider.extractUsername(extractedToken);

        if (username == null || !jwtTokenProvider.validateToken(extractedToken, username)) {
            logger.error("Unauthorized access attempt with token: {}", token);
            return new ResponseEntity<>(ResponseDto.error("Unauthorized", 401), HttpStatus.UNAUTHORIZED);
        }

        logger.info("Fetching transaction with ID: {} for user: {}", id, username);
        TransactionHistoryDto transaction = transactionHistoryBl.getTransactionHistoryById(id);
        return new ResponseEntity<>(ResponseDto.success(transaction, "Transaction fetched successfully"), HttpStatus.OK);
    }

    // Actualizar una transacción completa
    @PutMapping("/{id}")
    public ResponseEntity<ResponseDto<TransactionHistoryDto>> updateTransactionHistory(
            @PathVariable Integer id, @RequestBody TransactionHistoryDto dto,
            @RequestHeader("Authorization") String token) {
        String extractedToken = token.replace("Bearer ", "");
        String username = jwtTokenProvider.extractUsername(extractedToken);

        if (username == null || !jwtTokenProvider.validateToken(extractedToken, username)) {
            logger.error("Unauthorized access attempt with token: {}", token);
            return new ResponseEntity<>(ResponseDto.error("Unauthorized", 401), HttpStatus.UNAUTHORIZED);
        }

        logger.info("Updating transaction with ID: {} for user: {}", id, username);
        TransactionHistoryDto updatedTransaction = transactionHistoryBl.updateTransactionHistory(id, dto);
        return new ResponseEntity<>(ResponseDto.success(updatedTransaction, "Transaction updated successfully"), HttpStatus.OK);
    }

    // Eliminar una transacción
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto<Void>> deleteTransactionHistory(
            @PathVariable Integer id, @RequestHeader("Authorization") String token) {
        String extractedToken = token.replace("Bearer ", "");
        String username = jwtTokenProvider.extractUsername(extractedToken);

        if (username == null || !jwtTokenProvider.validateToken(extractedToken, username)) {
            logger.error("Unauthorized access attempt with token: {}", token);
            return new ResponseEntity<>(ResponseDto.error("Unauthorized", 401), HttpStatus.UNAUTHORIZED);
        }

        logger.info("Deleting transaction with ID: {} for user: {}", id, username);
        boolean deleted = transactionHistoryBl.deleteTransactionHistory(id);
        if (deleted) {
            return new ResponseEntity<>(ResponseDto.success(null, "Transaction deleted successfully"), HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(ResponseDto.error("Transaction not found", HttpStatus.NOT_FOUND.value()), HttpStatus.NOT_FOUND);
        }
    }

    // Nuevo Endpoint: Obtener transacciones por User ID
    @GetMapping("/user/{userId}")
    public ResponseEntity<ResponseDto<List<TransactionHistoryDto>>> getTransactionsByUserId(
            @PathVariable Integer userId,
            @RequestHeader("Authorization") String token) {

        String extractedToken = token.replace("Bearer ", "");
        String username = jwtTokenProvider.extractUsername(extractedToken);

        if (username == null || !jwtTokenProvider.validateToken(extractedToken, username)) {
            logger.error("Unauthorized access attempt with token: {}", token);
            return new ResponseEntity<>(ResponseDto.error("Unauthorized", 401), HttpStatus.UNAUTHORIZED);
        }

        logger.info("Fetching transactions for user with ID: {} requested by {}", userId, username);
        List<TransactionHistoryDto> transactions = transactionHistoryBl.getTransactionHistoriesByUserId(userId);
        return new ResponseEntity<>(ResponseDto.success(transactions, "Transactions fetched successfully"), HttpStatus.OK);
    }

    // Nuevo Endpoint: Obtener transacciones por Provider ID
    @GetMapping("/provider/{providerId}")
    public ResponseEntity<ResponseDto<List<TransactionHistoryDto>>> getTransactionsByProviderId(
            @PathVariable Integer providerId,
            @RequestHeader("Authorization") String token) {

        String extractedToken = token.replace("Bearer ", "");
        String username = jwtTokenProvider.extractUsername(extractedToken);

        if (username == null || !jwtTokenProvider.validateToken(extractedToken, username)) {
            logger.error("Unauthorized access attempt with token: {}", token);
            return new ResponseEntity<>(ResponseDto.error("Unauthorized", 401), HttpStatus.UNAUTHORIZED);
        }

        logger.info("Fetching transactions for provider with ID: {} requested by {}", providerId, username);
        List<TransactionHistoryDto> transactions = transactionHistoryBl.getTransactionHistoriesByProviderId(providerId);
        return new ResponseEntity<>(ResponseDto.success(transactions, "Transactions fetched successfully"), HttpStatus.OK);
    }


    // **Nuevo Endpoint: Actualizar solo el estado de una transacción**
    @PutMapping("/{id}/status")
    public ResponseEntity<ResponseDto<TransactionHistoryDto>> updateTransactionStatus(
            @PathVariable Integer id, @RequestParam String status,
            @RequestHeader("Authorization") String token) {
        String extractedToken = token.replace("Bearer ", "");
        String username = jwtTokenProvider.extractUsername(extractedToken);

        if (username == null || !jwtTokenProvider.validateToken(extractedToken, username)) {
            logger.error("Unauthorized access attempt with token: {}", token);
            return new ResponseEntity<>(ResponseDto.error("Unauthorized", 401), HttpStatus.UNAUTHORIZED);
        }

        logger.info("Updating status of transaction ID: {} to '{}'", id, status);
        try {
            TransactionHistoryDto updatedTransaction = transactionHistoryBl.updateTransactionStatus(id, status);
            return new ResponseEntity<>(ResponseDto.success(updatedTransaction, "Transaction status updated"), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(ResponseDto.error(e.getMessage(), HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);
        }
    }
}
