package com.project.pet_veteriana.bl;

import com.project.pet_veteriana.dto.SpecialtyDto;
import com.project.pet_veteriana.entity.Specialty;
import com.project.pet_veteriana.repository.SpecialtyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SpecialtyBl {

    @Autowired
    private SpecialtyRepository specialtyRepository;

    @Transactional
    public SpecialtyDto createSpecialty(SpecialtyDto specialtyDto) {
        Specialty specialty = new Specialty();
        specialty.setNameSpecialty(specialtyDto.getNameSpecialty());
        specialty.setCreatedAt(LocalDateTime.now());
        specialty = specialtyRepository.save(specialty);
        return mapToDto(specialty);
    }

    public List<SpecialtyDto> getAllSpecialties() {
        return specialtyRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public SpecialtyDto getSpecialtyById(Integer id) {
        return specialtyRepository.findById(id).map(this::mapToDto).orElse(null);
    }

    @Transactional
    public SpecialtyDto updateSpecialty(Integer id, SpecialtyDto specialtyDto) {
        return specialtyRepository.findById(id).map(specialty -> {
            specialty.setNameSpecialty(specialtyDto.getNameSpecialty());
            specialty = specialtyRepository.save(specialty);
            return mapToDto(specialty);
        }).orElse(null);
    }

    @Transactional
    public boolean deleteSpecialty(Integer id) {
        if (specialtyRepository.existsById(id)) {
            specialtyRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private SpecialtyDto mapToDto(Specialty specialty) {
        return new SpecialtyDto(specialty.getSpecialtyId(), specialty.getNameSpecialty(), specialty.getCreatedAt());
    }
}
