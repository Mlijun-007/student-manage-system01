package com.example.system_backend.exception;

import com.example.system_backend.dto.ResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    
    // 处理验证失败异常
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseDTO<Map<String, String>>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return ResponseEntity.badRequest().body(ResponseDTO.error("验证失败", errors));
    }
    
    // 处理非法参数异常
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ResponseDTO<Void>> handleIllegalArgumentException(IllegalArgumentException ex) {
        return ResponseEntity.badRequest().body(ResponseDTO.error(ex.getMessage(), null));
    }
    
    // 处理资源不存在异常
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ResponseDTO<Void>> handleNoSuchElementException(NoSuchElementException ex) {
        return ResponseEntity.notFound().build();
    }
    
    // 处理其他异常
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseDTO<Void>> handleException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ResponseDTO.error("服务器内部错误: " + ex.getMessage(), null));
    }
}
