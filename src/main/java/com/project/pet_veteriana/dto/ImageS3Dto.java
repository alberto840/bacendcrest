package com.project.pet_veteriana.dto;

import java.time.LocalDateTime;

public class ImageS3Dto {

    private Integer imageId;
    private String fileName;
    private String fileType;
    private String size;
    private LocalDateTime uploadDate;

    public ImageS3Dto() {
    }

    public ImageS3Dto(Integer imageId, String fileName, String fileType, String size, LocalDateTime uploadDate) {
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
