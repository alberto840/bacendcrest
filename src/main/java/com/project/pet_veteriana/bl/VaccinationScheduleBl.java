package com.project.pet_veteriana.bl;

import com.project.pet_veteriana.dto.VaccinationScheduleDto;
import com.project.pet_veteriana.entity.Pets;
import com.project.pet_veteriana.entity.Vaccination;
import com.project.pet_veteriana.entity.VaccinationSchedule;
import com.project.pet_veteriana.repository.PetsRepository;
import com.project.pet_veteriana.repository.VaccinationRepository;
import com.project.pet_veteriana.repository.VaccinationScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VaccinationScheduleBl {

    @Autowired
    private VaccinationScheduleRepository vaccinationScheduleRepository;

    @Autowired
    private PetsRepository petsRepository;

    @Autowired
    private VaccinationRepository vaccinationRepository;

    @Transactional
    public VaccinationScheduleDto createVaccinationSchedule(VaccinationScheduleDto dto) {
        Optional<Pets> petOptional = petsRepository.findById(dto.getPetId());
        Optional<Vaccination> vaccinationOptional = vaccinationRepository.findById(dto.getVaccinationId());

        if (petOptional.isEmpty() || vaccinationOptional.isEmpty()) {
            throw new IllegalArgumentException("Pet or Vaccination not found");
        }

        VaccinationSchedule schedule = new VaccinationSchedule();
        schedule.setDateVaccination(dto.getDateVaccination());
        schedule.setStatus(dto.getStatus());
        schedule.setCreatedAt(LocalDateTime.now());
        schedule.setPet(petOptional.get());
        schedule.setVaccination(vaccinationOptional.get());

        VaccinationSchedule savedSchedule = vaccinationScheduleRepository.save(schedule);
        return convertToDto(savedSchedule);
    }

    public List<VaccinationScheduleDto> getAllVaccinationSchedules() {
        List<VaccinationSchedule> schedules = vaccinationScheduleRepository.findAll();
        return schedules.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    public VaccinationScheduleDto getVaccinationScheduleById(Integer id) {
        VaccinationSchedule schedule = vaccinationScheduleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Vaccination Schedule not found"));
        return convertToDto(schedule);
    }

    @Transactional
    public VaccinationScheduleDto updateVaccinationSchedule(Integer id, VaccinationScheduleDto dto) {
        VaccinationSchedule schedule = vaccinationScheduleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Vaccination Schedule not found"));

        Optional<Pets> petOptional = petsRepository.findById(dto.getPetId());
        Optional<Vaccination> vaccinationOptional = vaccinationRepository.findById(dto.getVaccinationId());

        if (petOptional.isEmpty() || vaccinationOptional.isEmpty()) {
            throw new IllegalArgumentException("Pet or Vaccination not found");
        }

        schedule.setDateVaccination(dto.getDateVaccination());
        schedule.setStatus(dto.getStatus());
        schedule.setPet(petOptional.get());
        schedule.setVaccination(vaccinationOptional.get());

        VaccinationSchedule updatedSchedule = vaccinationScheduleRepository.save(schedule);
        return convertToDto(updatedSchedule);
    }

    @Transactional
    public boolean deleteVaccinationSchedule(Integer id) {
        if (vaccinationScheduleRepository.existsById(id)) {
            vaccinationScheduleRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private VaccinationScheduleDto convertToDto(VaccinationSchedule schedule) {
        return new VaccinationScheduleDto(
                schedule.getVaccinationScheduleId(),
                schedule.getDateVaccination(),
                schedule.getStatus(),
                schedule.getCreatedAt(),
                schedule.getPet().getPetId(),
                schedule.getVaccination().getVaccinationId()
        );
    }
}
