package com.project.pet_veteriana.bl;

import com.project.pet_veteriana.dto.SettingsDto;
import com.project.pet_veteriana.entity.Rol;
import com.project.pet_veteriana.entity.Settings;
import com.project.pet_veteriana.repository.RolRepository;
import com.project.pet_veteriana.repository.SettingsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SettingsBl {

    @Autowired
    private SettingsRepository settingsRepository;

    @Autowired
    private RolRepository rolRepository;

    public SettingsDto createSetting(SettingsDto settingsDto) {
        Settings settings = new Settings();
        settings.setKey(settingsDto.getKey());
        settings.setValue(settingsDto.getValue());
        settings.setCreatedAt(LocalDateTime.now());

        // Asocia el rol si existe
        Rol rol = rolRepository.findById(settingsDto.getRolId())
                .orElseThrow(() -> new IllegalArgumentException("Rol no encontrado con ID: " + settingsDto.getRolId()));
        settings.setRol(rol);

        Settings savedSettings = settingsRepository.save(settings);
        return convertToDto(savedSettings);
    }

    public List<SettingsDto> getAllSettings() {
        List<Settings> settingsList = settingsRepository.findAll();
        return settingsList.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    public SettingsDto getSettingById(Integer id) {
        Settings settings = settingsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Setting not found with ID: " + id));
        return convertToDto(settings);
    }

    public SettingsDto updateSetting(Integer id, SettingsDto settingsDto) {
        Settings settings = settingsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Setting not found with ID: " + id));

        settings.setKey(settingsDto.getKey());
        settings.setValue(settingsDto.getValue());
        // No sobrescribimos createdAt para preservar la fecha original

        // Actualiza el Rol si se especifica un nuevo ID
        if (settingsDto.getRolId() != null) {
            Rol rol = rolRepository.findById(settingsDto.getRolId())
                    .orElseThrow(() -> new IllegalArgumentException("Rol no encontrado con ID: " + settingsDto.getRolId()));
            settings.setRol(rol);
        }

        Settings updatedSettings = settingsRepository.save(settings);
        return convertToDto(updatedSettings);
    }

    public void deleteSetting(Integer id) {
        if (!settingsRepository.existsById(id)) {
            throw new RuntimeException("Setting not found with ID: " + id);
        }
        settingsRepository.deleteById(id);
    }

    private SettingsDto convertToDto(Settings settings) {
        SettingsDto settingsDto = new SettingsDto();
        settingsDto.setSettingsId(settings.getSettingsId());
        settingsDto.setKey(settings.getKey());
        settingsDto.setValue(settings.getValue());
        settingsDto.setCreatedAt(settings.getCreatedAt());
        settingsDto.setRolId(settings.getRol() != null ? settings.getRol().getRolId() : null);
        return settingsDto;
    }

    private Settings convertToEntity(SettingsDto settingsDto) {
        Settings settings = new Settings();
        settings.setSettingsId(settingsDto.getSettingsId());
        settings.setKey(settingsDto.getKey());
        settings.setValue(settingsDto.getValue());
        settings.setCreatedAt(settingsDto.getCreatedAt());

        if (settingsDto.getRolId() != null) {
            Rol rol = rolRepository.findById(settingsDto.getRolId())
                    .orElseThrow(() -> new IllegalArgumentException("Rol no encontrado con ID: " + settingsDto.getRolId()));
            settings.setRol(rol);
        }

        return settings;
    }
}
