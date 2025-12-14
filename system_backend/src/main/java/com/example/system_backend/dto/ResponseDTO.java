package com.example.system_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponseDTO<T> {
    private int code;
    private String message;
    private T data;
    
    public static <T> ResponseDTO<T> success(T data) {
        return new ResponseDTO<>(200, "操作成功", data);
    }
    
    public static <T> ResponseDTO<T> success(String message, T data) {
        return new ResponseDTO<>(200, message, data);
    }
    
    public static <T> ResponseDTO<T> error(int code, String message) {
        return new ResponseDTO<>(code, message, null);
    }
    
    public static <T> ResponseDTO<T> error(String message) {
        return new ResponseDTO<>(500, message, null);
    }
    
    public static <T> ResponseDTO<T> error(String message, T data) {
        return new ResponseDTO<>(400, message, data);
    }
}
