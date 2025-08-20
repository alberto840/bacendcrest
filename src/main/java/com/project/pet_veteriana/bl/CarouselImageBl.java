package com.project.pet_veteriana.bl;

import com.project.pet_veteriana.dto.CarouselImageDto;
import com.project.pet_veteriana.entity.CarouselImage;
import com.project.pet_veteriana.repository.CarouselImageRepository;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.GetPresignedObjectUrlArgs;
import io.minio.http.Method;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class CarouselImageBl {

    @Autowired
    private CarouselImageRepository carouselImageRepository;

    @Autowired
    private MinioClient minioClient;

    // Usamos la variable desde application.yml
    @Value("${minio.bucket-name}")
    private String bucketName;

    // Subir imagen al MinIO y guardar en la base de datos
    @Transactional
    public CarouselImageDto saveCarouselImage(MultipartFile file) {
        try {
            // 1. Generar nombre único
            String imageName = System.currentTimeMillis() + "-" + file.getOriginalFilename();

            // 2. Subir imagen a MinIO
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName) // Usamos la variable inyectada
                            .object(imageName)
                            .stream(file.getInputStream(), file.getSize(), -1)
                            .contentType(file.getContentType())
                            .build()
            );

            // 3. Guardar solo el nombre en la base de datos
            CarouselImage carouselImage = new CarouselImage(imageName);
            CarouselImage savedImage = carouselImageRepository.save(carouselImage);

            // 4. Retornar DTO con URL
            String url = getImageUrl(imageName);
            return new CarouselImageDto(savedImage.getId(), url);

        } catch (Exception e) {
            throw new RuntimeException("Error al subir la imagen: " + e.getMessage(), e);
        }
    }

    // Obtener URL pública desde MinIO
    private String getImageUrl(String imageName) {
        try {
            return minioClient.getPresignedObjectUrl(
                    GetPresignedObjectUrlArgs.builder()
                            .method(Method.GET)
                            .bucket(bucketName) // Usamos la variable inyectada
                            .object(imageName)
                            .expiry(7, TimeUnit.DAYS) // URL válida por 7 días
                            .build()
            );
        } catch (Exception e) {
            throw new RuntimeException("Error al generar URL de la imagen: " + e.getMessage(), e);
        }
    }

    // Obtener las últimas 4 imágenes subidas
    public List<CarouselImageDto> getAllCarouselImages() {
        // Últimos 4 registros por ID descendente
        List<CarouselImage> images = carouselImageRepository.findAllByOrderByIdDesc(PageRequest.of(0, 7));
        return images.stream()
                .map(img -> new CarouselImageDto(
                        img.getId(),
                        getImageUrl(img.getImageName())
                ))
                .collect(Collectors.toList());
    }
}
