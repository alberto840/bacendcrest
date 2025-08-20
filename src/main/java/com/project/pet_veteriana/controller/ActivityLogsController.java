package com.project.pet_veteriana.controller;

import com.project.pet_veteriana.bl.ActivityLogsBl;
import com.project.pet_veteriana.dto.ActivityLogsDto;
import com.project.pet_veteriana.dto.ResponseDto;
import com.project.pet_veteriana.config.JwtTokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/activity-logs")
public class ActivityLogsController {

    @Autowired
    private ActivityLogsBl activityLogsBl;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    // Crear un nuevo ActivityLog
    @PostMapping
    public ResponseDto<ActivityLogsDto> createActivityLog(HttpServletRequest request, @RequestBody ActivityLogsDto activityLogsDto) {
        String token = request.getHeader("Authorization");

        // Validar el token JWT antes de proceder
        String extractedToken = token.replace("Bearer ", "");
        String username = jwtTokenProvider.extractUsername(extractedToken);

        if (username == null || !jwtTokenProvider.validateToken(extractedToken, username)) {
            return ResponseDto.error("Unauthorized", 401); // Retornar 401 Unauthorized si el token no es válido
        }

        // Ahora pasamos el token junto con el ActivityLogsDto al método
        ActivityLogsDto createdLog = activityLogsBl.createActivityLog(extractedToken, activityLogsDto);
        return ResponseDto.success(createdLog, "Activity log created successfully");
    }


    // Obtener todos los ActivityLogs
    @GetMapping
    public ResponseDto<List<ActivityLogsDto>> getAllActivityLogs(@RequestHeader("Authorization") String token) {
        // Validar el token JWT antes de proceder
        String extractedToken = token.replace("Bearer ", "");
        String username = jwtTokenProvider.extractUsername(extractedToken);

        if (username == null || !jwtTokenProvider.validateToken(extractedToken, username)) {
            return ResponseDto.error("Unauthorized", 401); // Retornar 401 Unauthorized si el token no es válido
        }

        List<ActivityLogsDto> logs = activityLogsBl.getAllActivityLogs();
        return ResponseDto.success(logs, "Activity logs fetched successfully");
    }

    // Obtener un ActivityLog por ID
    @GetMapping("/{id}")
    public ResponseDto<ActivityLogsDto> getActivityLogById(@PathVariable Integer id, @RequestHeader("Authorization") String token) {
        // Validar el token JWT antes de proceder
        String extractedToken = token.replace("Bearer ", "");
        String username = jwtTokenProvider.extractUsername(extractedToken);

        if (username == null || !jwtTokenProvider.validateToken(extractedToken, username)) {
            return ResponseDto.error("Unauthorized", 401); // Retornar 401 Unauthorized si el token no es válido
        }

        ActivityLogsDto log = activityLogsBl.getActivityLogById(id);
        return ResponseDto.success(log, "Activity log fetched successfully");
    }

    // Actualizar un ActivityLog
    @PutMapping("/{id}")
    public ResponseDto<ActivityLogsDto> updateActivityLog(@PathVariable Integer id, @RequestBody ActivityLogsDto activityLogsDto, @RequestHeader("Authorization") String token) {
        // Validar el token JWT antes de proceder
        String extractedToken = token.replace("Bearer ", "");
        String username = jwtTokenProvider.extractUsername(extractedToken);

        if (username == null || !jwtTokenProvider.validateToken(extractedToken, username)) {
            return ResponseDto.error("Unauthorized", 401); // Retornar 401 Unauthorized si el token no es válido
        }

        ActivityLogsDto updatedLog = activityLogsBl.updateActivityLog(id, activityLogsDto);
        return ResponseDto.success(updatedLog, "Activity log updated successfully");
    }

    // Eliminar un ActivityLog
    @DeleteMapping("/{id}")
    public ResponseDto<String> deleteActivityLog(@PathVariable Integer id, @RequestHeader("Authorization") String token) {
        // Validar el token JWT antes de proceder
        String extractedToken = token.replace("Bearer ", "");
        String username = jwtTokenProvider.extractUsername(extractedToken);

        if (username == null || !jwtTokenProvider.validateToken(extractedToken, username)) {
            return ResponseDto.error("Unauthorized", 401); // Retornar 401 Unauthorized si el token no es válido
        }

        activityLogsBl.deleteActivityLog(id);
        return ResponseDto.success("Deleted", "Activity log deleted successfully");
    }
}
