package com.project.pet_veteriana.controller;

import com.project.pet_veteriana.bl.SupportTicketsBl;
import com.project.pet_veteriana.dto.ResponseDto;
import com.project.pet_veteriana.dto.SupportTicketsDto;
import com.project.pet_veteriana.config.JwtTokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/support-tickets")
public class SupportTicketsController {

    private static final Logger logger = LoggerFactory.getLogger(SupportTicketsController.class);

    @Autowired
    private SupportTicketsBl supportTicketsBl;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    // Crear un nuevo ticket de soporte
    @PostMapping
    public ResponseEntity<ResponseDto<SupportTicketsDto>> createTicket(HttpServletRequest request, @RequestBody SupportTicketsDto supportTicketsDto) {
        String token = request.getHeader("Authorization");
        String extractedToken = token.replace("Bearer ", "");
        String username = jwtTokenProvider.extractUsername(extractedToken);

        if (username == null || !jwtTokenProvider.validateToken(extractedToken, username)) {
            logger.error("Acceso no autorizado con el token: {}", token);
            return new ResponseEntity<>(ResponseDto.error("Unauthorized", 401), HttpStatus.UNAUTHORIZED);
        }

        SupportTicketsDto createdTicket = supportTicketsBl.createTicket(supportTicketsDto);
        ResponseDto<SupportTicketsDto> response = ResponseDto.success(createdTicket, "Ticket de soporte creado exitosamente");
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // Obtener todos los tickets de soporte
    @GetMapping
    public ResponseEntity<ResponseDto<List<SupportTicketsDto>>> getAllTickets(@RequestHeader("Authorization") String token) {
        String extractedToken = token.replace("Bearer ", "");
        String username = jwtTokenProvider.extractUsername(extractedToken);

        if (username == null || !jwtTokenProvider.validateToken(extractedToken, username)) {
            logger.error("Acceso no autorizado con el token: {}", token);
            return new ResponseEntity<>(ResponseDto.error("Unauthorized", 401), HttpStatus.UNAUTHORIZED);
        }

        List<SupportTicketsDto> tickets = supportTicketsBl.getAllTickets();
        ResponseDto<List<SupportTicketsDto>> response = ResponseDto.success(tickets, "Tickets obtenidos exitosamente");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Obtener ticket de soporte por ID
    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto<SupportTicketsDto>> getTicketById(@PathVariable Integer id, @RequestHeader("Authorization") String token) {
        String extractedToken = token.replace("Bearer ", "");
        String username = jwtTokenProvider.extractUsername(extractedToken);

        if (username == null || !jwtTokenProvider.validateToken(extractedToken, username)) {
            logger.error("Acceso no autorizado con el token: {}", token);
            return new ResponseEntity<>(ResponseDto.error("Unauthorized", 401), HttpStatus.UNAUTHORIZED);
        }

        SupportTicketsDto ticket = supportTicketsBl.getTicketById(id);
        ResponseDto<SupportTicketsDto> response = ResponseDto.success(ticket, "Ticket obtenido exitosamente");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Actualizar ticket de soporte
    @PutMapping("/{id}")
    public ResponseEntity<ResponseDto<SupportTicketsDto>> updateTicket(@PathVariable Integer id, @RequestBody SupportTicketsDto supportTicketsDto, @RequestHeader("Authorization") String token) {
        String extractedToken = token.replace("Bearer ", "");
        String username = jwtTokenProvider.extractUsername(extractedToken);

        if (username == null || !jwtTokenProvider.validateToken(extractedToken, username)) {
            logger.error("Acceso no autorizado con el token: {}", token);
            return new ResponseEntity<>(ResponseDto.error("Unauthorized", 401), HttpStatus.UNAUTHORIZED);
        }

        SupportTicketsDto updatedTicket = supportTicketsBl.updateTicket(id, supportTicketsDto);
        ResponseDto<SupportTicketsDto> response = ResponseDto.success(updatedTicket, "Ticket actualizado exitosamente");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Eliminar ticket de soporte
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto<Void>> deleteTicket(@PathVariable Integer id, @RequestHeader("Authorization") String token) {
        String extractedToken = token.replace("Bearer ", "");
        String username = jwtTokenProvider.extractUsername(extractedToken);

        if (username == null || !jwtTokenProvider.validateToken(extractedToken, username)) {
            logger.error("Acceso no autorizado con el token: {}", token);
            return new ResponseEntity<>(ResponseDto.error("Unauthorized", 401), HttpStatus.UNAUTHORIZED);
        }

        boolean isDeleted = supportTicketsBl.deleteTicket(id);
        if (isDeleted) {
            ResponseDto<Void> response = ResponseDto.success(null, "Ticket eliminado exitosamente");
            return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
        } else {
            ResponseDto<Void> response = ResponseDto.error("Ticket no encontrado", HttpStatus.NOT_FOUND.value());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }
}
