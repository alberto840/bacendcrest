package com.project.pet_veteriana.entity;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "ImageS3")

public class ImageS3 implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id", nullable = false)
    private Integer imageId;

    @Column(name = "file_name", nullable = false, length = 150)
    private String fileName;

    @Column(name = "file_type", nullable = false, length = 50)
    private String fileType;

    @Column(name = "size", nullable = false, length = 50)
    private String size;

    @Column(name = "upload_date", nullable = false)
    private LocalDateTime uploadDate;


    public ImageS3() {
    }

    public ImageS3(Integer imageId, String fileName, String fileType, String size, LocalDateTime uploadDate) {
        this.imageId = imageId;
        this.fileName = fileName;
        this.fileType = fileType;
        this.size = size;
        this.uploadDate = uploadDate;
    }

    public Integer getImageId() {
        return imageId;
    }

    public void setImageId(Integer imageId) {
        this.imageId = imageId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public LocalDateTime getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(LocalDateTime uploadDate) {
        this.uploadDate = uploadDate;
    }
}
