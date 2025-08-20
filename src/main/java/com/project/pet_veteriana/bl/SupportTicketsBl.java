package com.project.pet_veteriana.bl;

import com.project.pet_veteriana.dto.SupportTicketsDto;
import com.project.pet_veteriana.entity.SupportTickets;
import com.project.pet_veteriana.entity.Users;
import com.project.pet_veteriana.repository.SupportTicketsRepository;
import com.project.pet_veteriana.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SupportTicketsBl {

    @Autowired
    private SupportTicketsRepository supportTicketsRepository;

    @Autowired
    private UsersRepository usersRepository;

    private final List<String> VALID_STATUSES = List.of("Nuevo", "Proceso", "Resuelto");

    // Crear un nuevo ticket de soporte
    @Transactional
    public SupportTicketsDto createTicket(SupportTicketsDto supportTicketsDto) {
        Optional<Users> userOptional = usersRepository.findById(supportTicketsDto.getUserId());
        if (userOptional.isEmpty()) {
            throw new IllegalArgumentException("Usuario no encontrado");
        }

        if (!VALID_STATUSES.contains(supportTicketsDto.getStatus())) {
            throw new IllegalArgumentException("Estado inválido. Debe ser: Nuevo, Proceso o Resuelto.");
        }

        SupportTickets ticket = new SupportTickets();
        ticket.setSubject(supportTicketsDto.getSubject());
        ticket.setDescription(supportTicketsDto.getDescription());
        ticket.setStatus(supportTicketsDto.getStatus());
        ticket.setCreatedAt(LocalDateTime.now());
        ticket.setUpdatedAt(LocalDateTime.now());
        ticket.setUser(userOptional.get());

        SupportTickets savedTicket = supportTicketsRepository.save(ticket);
        return convertToDto(savedTicket);
    }

    // Obtener todos los tickets
    public List<SupportTicketsDto> getAllTickets() {
        List<SupportTickets> tickets = supportTicketsRepository.findAll();
        return tickets.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    // Obtener ticket por ID
    public SupportTicketsDto getTicketById(Integer id) {
        Optional<SupportTickets> ticketOptional = supportTicketsRepository.findById(id);
        if (ticketOptional.isEmpty()) {
            throw new IllegalArgumentException("Ticket no encontrado");
        }
        return convertToDto(ticketOptional.get());
    }

    // Actualizar ticket
    @Transactional
    public SupportTicketsDto updateTicket(Integer id, SupportTicketsDto supportTicketsDto) {
        Optional<SupportTickets> ticketOptional = supportTicketsRepository.findById(id);
        if (ticketOptional.isEmpty()) {
            throw new IllegalArgumentException("Ticket no encontrado");
        }

        if (!VALID_STATUSES.contains(supportTicketsDto.getStatus())) {
            throw new IllegalArgumentException("Estado inválido. Debe ser: Nuevo, Proceso o Resuelto.");
        }

        SupportTickets ticket = ticketOptional.get();
        ticket.setSubject(supportTicketsDto.getSubject());
        ticket.setDescription(supportTicketsDto.getDescription());
        ticket.setStatus(supportTicketsDto.getStatus());
        ticket.setUpdatedAt(LocalDateTime.now());

        SupportTickets updatedTicket = supportTicketsRepository.save(ticket);
        return convertToDto(updatedTicket);
    }

    // Eliminar ticket
    @Transactional
    public boolean deleteTicket(Integer id) {
        if (supportTicketsRepository.existsById(id)) {
            supportTicketsRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Convertir entidad a DTO
    private SupportTicketsDto convertToDto(SupportTickets ticket) {
        return new SupportTicketsDto(
                ticket.getSupportTicketsId(),
                ticket.getSubject(),
                ticket.getDescription(),
                ticket.getStatus(),
                ticket.getUpdatedAt(),
                ticket.getCreatedAt(),
                ticket.getUser().getUserId()
        );
    }
}
