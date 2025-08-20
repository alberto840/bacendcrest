package com.project.pet_veteriana.bl;

import com.project.pet_veteriana.dto.MedicalHistoryDto;
import com.project.pet_veteriana.entity.MedicalHistory;
import com.project.pet_veteriana.repository.MedicalHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MedicalHistoryBl {

    @Autowired
    private MedicalHistoryRepository medicalHistoryRepository;

    public List<MedicalHistoryDto> findAll() {
        return medicalHistoryRepository.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    public MedicalHistoryDto findById(Integer id) {
        Optional<MedicalHistory> optional = medicalHistoryRepository.findById(id);
        return optional.map(this::toDto).orElse(null);
    }

    @Transactional
    public MedicalHistoryDto create(MedicalHistoryDto dto) {
        MedicalHistory entity = toEntity(dto);
        entity.setCreatedAt(LocalDateTime.now());
        MedicalHistory saved = medicalHistoryRepository.save(entity);
        return toDto(saved);
    }

    @Transactional
    public MedicalHistoryDto update(Integer id, MedicalHistoryDto dto) {
        Optional<MedicalHistory> optional = medicalHistoryRepository.findById(id);
        if (optional.isPresent()) {
            MedicalHistory entity = optional.get();
            entity.setDate(dto.getDate());
            entity.setVisitReason(dto.getVisitReason());
            entity.setSymptoms(dto.getSymptoms());
            entity.setPet(entity.getPet());
            MedicalHistory updated = medicalHistoryRepository.save(entity);
            return toDto(updated);
        }
        return null;
    }

    @Transactional
    public boolean delete(Integer id) {
        if (medicalHistoryRepository.existsById(id)) {
            medicalHistoryRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private MedicalHistoryDto toDto(MedicalHistory entity) {
        return new MedicalHistoryDto(
                entity.getMedicalHistoryId(),
                entity.getDate(),
                entity.getVisitReason(),
                entity.getSymptoms(),
                entity.getCreatedAt(),
                entity.getPet().getPetId()
        );
    }

    private MedicalHistory toEntity(MedicalHistoryDto dto) {
        MedicalHistory entity = new MedicalHistory();
        entity.setMedicalHistoryId(dto.getMedicalHistoryId());
        entity.setDate(dto.getDate());
        entity.setVisitReason(dto.getVisitReason());
        entity.setSymptoms(dto.getSymptoms());
        return entity;
    }
}

