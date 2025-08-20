package com.project.pet_veteriana.bl;

import com.project.pet_veteriana.dto.VaccinationDto;
import com.project.pet_veteriana.entity.Vaccination;
import com.project.pet_veteriana.repository.VaccinationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class VaccinationBl {

    @Autowired
    private VaccinationRepository vaccinationRepository;

    public VaccinationDto createVaccination(VaccinationDto vaccinationDto) {
        Vaccination vaccination = new Vaccination();
        vaccination.setName(vaccinationDto.getName());
        vaccination.setCreatedAt(LocalDateTime.now());
        Vaccination saved = vaccinationRepository.save(vaccination);
        return new VaccinationDto(saved.getVaccinationId(), saved.getName(), saved.getCreatedAt());
    }

    public List<VaccinationDto> getAllVaccinations() {
        return vaccinationRepository.findAll().stream()
                .map(v -> new VaccinationDto(v.getVaccinationId(), v.getName(), v.getCreatedAt()))
                .collect(Collectors.toList());
    }

    public VaccinationDto getVaccinationById(Integer id) {
        return vaccinationRepository.findById(id)
                .map(v -> new VaccinationDto(v.getVaccinationId(), v.getName(), v.getCreatedAt()))
                .orElse(null);
    }

    @Transactional
    public VaccinationDto updateVaccination(Integer id, VaccinationDto vaccinationDto) {
        return vaccinationRepository.findById(id).map(v -> {
            v.setName(vaccinationDto.getName());
            vaccinationRepository.save(v);
            return new VaccinationDto(v.getVaccinationId(), v.getName(), v.getCreatedAt());
        }).orElse(null);
    }

    @Transactional
    public boolean deleteVaccination(Integer id) {
        if (vaccinationRepository.existsById(id)) {
            vaccinationRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
