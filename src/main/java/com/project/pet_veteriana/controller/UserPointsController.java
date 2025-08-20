package com.project.pet_veteriana.controller;

import com.project.pet_veteriana.dto.UserPointsDto;
import com.project.pet_veteriana.dto.ResponseDto;
import com.project.pet_veteriana.bl.UserPointsBl;
import com.project.pet_veteriana.config.JwtTokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user-points")
public class UserPointsController {

    @Autowired
    private UserPointsBl userPointsBl;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    // Crear un nuevo UserPoint
    @PostMapping
    public ResponseDto<UserPointsDto> createUserPoint(HttpServletRequest request, @RequestBody UserPointsDto userPointsDto) {
        String token = request.getHeader("Authorization");

        // Validar el token JWT antes de proceder
        String extractedToken = token.replace("Bearer ", "");
        String username = jwtTokenProvider.extractUsername(extractedToken);

        if (username == null || !jwtTokenProvider.validateToken(extractedToken, username)) {
            return ResponseDto.error("Unauthorized", 401); // Retornar 401 Unauthorized si el token no es válido
        }

        UserPointsDto createdUserPoint = userPointsBl.createUserPoint(extractedToken, userPointsDto);
        return ResponseDto.success(createdUserPoint, "User points created successfully");
    }

    // Obtener todos los UserPoints
    @GetMapping
    public ResponseDto<List<UserPointsDto>> getAllUserPoints(@RequestHeader("Authorization") String token) {
        // Validar el token JWT antes de proceder
        String extractedToken = token.replace("Bearer ", "");
        String username = jwtTokenProvider.extractUsername(extractedToken);

        if (username == null || !jwtTokenProvider.validateToken(extractedToken, username)) {
            return ResponseDto.error("Unauthorized", 401); // Retornar 401 Unauthorized si el token no es válido
        }

        List<UserPointsDto> points = userPointsBl.getAllUserPoints();
        return ResponseDto.success(points, "User points fetched successfully");
    }

    // Obtener UserPoint por ID
    @GetMapping("/{id}")
    public ResponseDto<UserPointsDto> getUserPointById(@PathVariable Integer id, @RequestHeader("Authorization") String token) {
        // Validar el token JWT antes de proceder
        String extractedToken = token.replace("Bearer ", "");
        String username = jwtTokenProvider.extractUsername(extractedToken);

        if (username == null || !jwtTokenProvider.validateToken(extractedToken, username)) {
            return ResponseDto.error("Unauthorized", 401); // Retornar 401 Unauthorized si el token no es válido
        }

        UserPointsDto point = userPointsBl.getUserPointById(id);
        return ResponseDto.success(point, "User points fetched successfully");
    }

    // Actualizar UserPoint
    @PutMapping("/{id}")
    public ResponseDto<UserPointsDto> updateUserPoint(@PathVariable Integer id, @RequestBody UserPointsDto userPointsDto, @RequestHeader("Authorization") String token) {
        // Validar el token JWT antes de proceder
        String extractedToken = token.replace("Bearer ", "");
        String username = jwtTokenProvider.extractUsername(extractedToken);

        if (username == null || !jwtTokenProvider.validateToken(extractedToken, username)) {
            return ResponseDto.error("Unauthorized", 401); // Retornar 401 Unauthorized si el token no es válido
        }

        UserPointsDto updatedUserPoint = userPointsBl.updateUserPoint(id, userPointsDto);
        return ResponseDto.success(updatedUserPoint, "User points updated successfully");
    }

    // Eliminar UserPoint
    @DeleteMapping("/{id}")
    public ResponseDto<String> deleteUserPoint(@PathVariable Integer id, @RequestHeader("Authorization") String token) {
        // Validar el token JWT antes de proceder
        String extractedToken = token.replace("Bearer ", "");
        String username = jwtTokenProvider.extractUsername(extractedToken);

        if (username == null || !jwtTokenProvider.validateToken(extractedToken, username)) {
            return ResponseDto.error("Unauthorized", 401); // Retornar 401 Unauthorized si el token no es válido
        }

        userPointsBl.deleteUserPoint(id);
        return ResponseDto.success("Deleted", "User points deleted successfully");
    }
}
