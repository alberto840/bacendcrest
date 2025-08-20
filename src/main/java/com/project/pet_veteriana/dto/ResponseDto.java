package com.project.pet_veteriana.dto;

public class ResponseDto<T> {

    private String message;
    private String status;
    private T data;
    private int code;

    // Constructor por defecto
    public ResponseDto() {
    }

    // Constructor con parámetros
    public ResponseDto(String message, String status, T data, int code) {
        this.message = message;
        this.status = status;
        this.data = data;
        this.code = code;
        
    }

    // Getter y Setter para message
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    // Getter y Setter para status
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // Getter y Setter para data
    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    // Getter y Setter para code
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    // Método para crear respuestas exitosas
    public static <T> ResponseDto<T> success(T data, String message) {
        return new ResponseDto<T>(message, "success", data, 200);
    }

    // Método para crear respuestas con error
    public static <T> ResponseDto<T> error(String message, int code) {
        return new ResponseDto<T>(message, "error", null, code);
    }
}
