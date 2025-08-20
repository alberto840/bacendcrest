package com.project.pet_veteriana.bl;

import com.project.pet_veteriana.dto.ImageS3Dto;
import com.project.pet_veteriana.entity.ImageS3;
import com.project.pet_veteriana.repository.ImageS3Repository;
import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.http.Method;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ImagesS3Bl {

    @Autowired
    private MinioClient minioClient;

    @Autowired
    private ImageS3Repository imageS3Repository;

    @Value("${minio.bucket-name}")
    private String bucketName;

    // Método para subir un archivo a MinIO y guardarlo en la base de datos
    @Transactional
    public ImageS3Dto uploadFile(MultipartFile file) throws Exception {
        String fileName = file.getOriginalFilename();
        String contentType = file.getContentType();
        String fileSize = String.valueOf(file.getSize());

        // Subir archivo a MinIO
        minioClient.putObject(
                PutObjectArgs.builder()
                        .bucket(bucketName)
                        .object(fileName)
                        .stream(file.getInputStream(), file.getSize(), -1)
                        .contentType(contentType)
                        .build()
        );

        // Generar la URL del archivo
        //String fileUrl = generateFileUrl(fileName);

        // Crear un objeto ImageS3 para guardar en la base de datos
        ImageS3 imageS3 = new ImageS3();
        imageS3.setFileName(fileName);
        imageS3.setFileType(contentType);
        imageS3.setSize(fileSize);
        imageS3.setUploadDate(java.time.LocalDateTime.now());

        // Guardar el objeto ImageS3 en la base de datos
        imageS3Repository.save(imageS3);

        // Crear y retornar el DTO con los detalles del archivo
        ImageS3Dto imageS3Dto = new ImageS3Dto(imageS3.getImageId(), fileName, contentType, fileSize, imageS3.getUploadDate());
        return imageS3Dto;
    }

    // Método para generar el enlace del archivo subido    
    public String generateFileUrl(String filename) {
        try {
            return minioClient.getPresignedObjectUrl(
                    GetPresignedObjectUrlArgs.builder()
                            .bucket(bucketName)
                            .object(filename)
                            .method(Method.GET)
                            .build()
            );
        } catch (Exception e) {
            throw new RuntimeException("Error generating file URL", e);
        }
    }

    // Método para eliminar un archivo de MinIO
    @Transactional
    public void deleteFile(Integer imageId) {
        try {
            Optional<ImageS3> optionalImage = imageS3Repository.findById(imageId);

            if (optionalImage.isPresent()) {
                ImageS3 image = optionalImage.get();

                // Eliminar el archivo de MinIO
                minioClient.removeObject(
                        io.minio.RemoveObjectArgs.builder()
                                .bucket(bucketName)
                                .object(image.getFileName())
                                .build()
                );

                // Eliminar el registro del archivo de la base de datos
                imageS3Repository.delete(image);
            } else {
                throw new RuntimeException("No se encontró la imagen con el ID especificado.");
            }
        } catch (Exception e) {
            throw new RuntimeException("Error deleting file from MinIO or database", e);
        }
    }


    // Método para listar todos los archivos subidos
    public List<ImageS3Dto> listUploadedFiles() {
        // Obtener todos los registros desde la base de datos
        List<ImageS3> imageS3s = imageS3Repository.findAll();

        // Convertir los objetos de la base de datos a DTOs
        return imageS3s.stream()
                .map(imageS3 -> new ImageS3Dto(
                        imageS3.getImageId(),
                        imageS3.getFileName(),
                        imageS3.getFileType(),
                        imageS3.getSize(),
                        imageS3.getUploadDate()
                ))
                .collect(Collectors.toList());
    }
}
