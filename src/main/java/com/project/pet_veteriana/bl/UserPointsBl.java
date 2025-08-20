package com.project.pet_veteriana.bl;

import com.project.pet_veteriana.dto.UserPointsDto;
import com.project.pet_veteriana.entity.UserPoints;
import com.project.pet_veteriana.entity.Users;
import com.project.pet_veteriana.repository.UserPointsRepository;
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
public class UserPointsBl {

    private static final Logger logger = LoggerFactory.getLogger(UserPointsBl.class);

    @Autowired
    private UserPointsRepository userPointsRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    // Crear un nuevo UserPoint
    @Transactional
    public UserPointsDto createUserPoint(String token, UserPointsDto userPointsDto) {
        // Validar el token JWT
        String username = jwtTokenProvider.extractUsername(token);

        if (username == null || !jwtTokenProvider.validateToken(token, username)) {
            throw new RuntimeException("Unauthorized access");
        }

        // Buscar el usuario en la base de datos usando el email extraído del token
        Optional<Users> user = usersRepository.findByEmail(username);

        if (user.isPresent()) {
            UserPoints userPoints = new UserPoints();
            userPoints.setPoints(userPointsDto.getPoints());
            userPoints.setDescription(userPointsDto.getDescription());
            userPoints.setCreatedAt(LocalDateTime.now());
            userPoints.setUser(user.get());

            userPoints = userPointsRepository.save(userPoints);

            logger.info("User points created for user: {}", user.get().getEmail());

            return new UserPointsDto(
                    userPoints.getUserPointsId(),
                    userPoints.getPoints(),
                    userPoints.getDescription(),
                    userPoints.getCreatedAt(),
                    userPoints.getUser().getUserId()
            );
        } else {
            throw new RuntimeException("User not found");
        }
    }

    // Obtener todos los UserPoints
    public List<UserPointsDto> getAllUserPoints() {
        List<UserPoints> points = userPointsRepository.findAll();
        return points.stream().map(point -> new UserPointsDto(
                point.getUserPointsId(),
                point.getPoints(),
                point.getDescription(),
                point.getCreatedAt(),
                point.getUser().getUserId()
        )).toList();
    }

    // Obtener UserPoint por ID
    public UserPointsDto getUserPointById(Integer id) {
        Optional<UserPoints> point = userPointsRepository.findById(id);
        if (point.isPresent()) {
            UserPoints userPoints = point.get();
            return new UserPointsDto(
                    userPoints.getUserPointsId(),
                    userPoints.getPoints(),
                    userPoints.getDescription(),
                    userPoints.getCreatedAt(),
                    userPoints.getUser().getUserId()
            );
        } else {
            throw new RuntimeException("User points not found");
        }
    }

    // Actualizar UserPoint
    @Transactional
    public UserPointsDto updateUserPoint(Integer id, UserPointsDto userPointsDto) {
        Optional<UserPoints> point = userPointsRepository.findById(id);
        if (point.isPresent()) {
            UserPoints userPoints = point.get();
            userPoints.setPoints(userPointsDto.getPoints());
            userPoints.setDescription(userPointsDto.getDescription());

            userPoints = userPointsRepository.save(userPoints);

            logger.info("User points updated for ID: {}", id);

            return new UserPointsDto(
                    userPoints.getUserPointsId(),
                    userPoints.getPoints(),
                    userPoints.getDescription(),
                    userPoints.getCreatedAt(),
                    userPoints.getUser().getUserId()
            );
        } else {
            throw new RuntimeException("User points not found");
        }
    }

    // Eliminar UserPoint
    @Transactional
    public void deleteUserPoint(Integer id) {
        userPointsRepository.deleteById(id);
        logger.info("User points deleted for ID: {}", id);
    }
}
