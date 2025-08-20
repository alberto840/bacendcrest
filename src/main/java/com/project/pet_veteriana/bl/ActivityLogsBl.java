package com.project.pet_veteriana.bl;

import com.project.pet_veteriana.dto.ActivityLogsDto;
import com.project.pet_veteriana.entity.ActivityLogs;
import com.project.pet_veteriana.entity.Users;
import com.project.pet_veteriana.repository.ActivityLogsRepository;
import com.project.pet_veteriana.repository.UsersRepository;
import com.project.pet_veteriana.config.JwtTokenProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ActivityLogsBl {

    private static final Logger logger = LoggerFactory.getLogger(ActivityLogsBl.class);

    @Autowired
    private ActivityLogsRepository activityLogsRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    // Crear un nuevo ActivityLog
    @Transactional
    public ActivityLogsDto createActivityLog(String token, ActivityLogsDto activityLogsDto) {
        // Validar el token JWT
        String username = jwtTokenProvider.extractUsername(token);

        // Si el username (email) es inválido o no se puede validar el token, lanzar excepción
        if (username == null || !jwtTokenProvider.validateToken(token, username)) {
            throw new RuntimeException("Unauthorized access");
        }

        // Buscar el usuario en la base de datos usando el email extraído del token
        Optional<Users> user = usersRepository.findByEmail(username);

        if (user.isPresent()) {
            ActivityLogs activityLogs = new ActivityLogs();
            activityLogs.setUser(user.get());
            activityLogs.setAction(activityLogsDto.getAction());
            activityLogs.setDescription(activityLogsDto.getDescription());
            activityLogs.setIp(activityLogsDto.getIp());
            activityLogs.setCreatedAt(LocalDateTime.now());

            activityLogs = activityLogsRepository.save(activityLogs);

            logger.info("Activity log created for user: {}", user.get().getEmail());

            return new ActivityLogsDto(
                    activityLogs.getActivityLogsId(),
                    activityLogs.getUser().getUserId(),
                    activityLogs.getAction(),
                    activityLogs.getDescription(),
                    activityLogs.getIp(),
                    activityLogs.getCreatedAt()
            );
        } else {
            throw new RuntimeException("User not found");
        }
    }

    // Obtener todos los ActivityLogs
    public List<ActivityLogsDto> getAllActivityLogs() {
        List<ActivityLogs> logs = activityLogsRepository.findAll();
        return logs.stream().map(log -> new ActivityLogsDto(
                log.getActivityLogsId(),
                log.getUser().getUserId(),
                log.getAction(),
                log.getDescription(),
                log.getIp(),
                log.getCreatedAt()
        )).toList();
    }

    // Obtener ActivityLog por ID
    public ActivityLogsDto getActivityLogById(Integer id) {
        Optional<ActivityLogs> log = activityLogsRepository.findById(id);
        if (log.isPresent()) {
            ActivityLogs activityLogs = log.get();
            return new ActivityLogsDto(
                    activityLogs.getActivityLogsId(),
                    activityLogs.getUser().getUserId(),
                    activityLogs.getAction(),
                    activityLogs.getDescription(),
                    activityLogs.getIp(),
                    activityLogs.getCreatedAt()
            );
        } else {
            throw new RuntimeException("Activity log not found");
        }
    }

    // Actualizar ActivityLog
    @Transactional
    public ActivityLogsDto updateActivityLog(Integer id, ActivityLogsDto activityLogsDto) {
        Optional<ActivityLogs> log = activityLogsRepository.findById(id);
        if (log.isPresent()) {
            ActivityLogs activityLogs = log.get();
            activityLogs.setAction(activityLogsDto.getAction());
            activityLogs.setDescription(activityLogsDto.getDescription());
            activityLogs.setIp(activityLogsDto.getIp());
            activityLogs.setCreatedAt(activityLogsDto.getCreatedAt());

            activityLogs = activityLogsRepository.save(activityLogs);

            logger.info("Activity log updated for ID: {}", id);

            return new ActivityLogsDto(
                    activityLogs.getActivityLogsId(),
                    activityLogs.getUser().getUserId(),
                    activityLogs.getAction(),
                    activityLogs.getDescription(),
                    activityLogs.getIp(),
                    activityLogs.getCreatedAt()
            );
        } else {
            throw new RuntimeException("Activity log not found");
        }
    }

    // Eliminar ActivityLog
    @Transactional
    public void deleteActivityLog(Integer id) {
        activityLogsRepository.deleteById(id);
        logger.info("Activity log deleted for ID: {}", id);
    }
}
