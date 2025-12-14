package com.example.system_backend.dto;

import lombok.Data;
import jakarta.validation.constraints.NotNull;

@Data
public class EnrollmentDTO {
    private Long id;
    
    @NotNull(message = "学生ID不能为空")
    private Long studentId;
    
    @NotNull(message = "课程ID不能为空")
    private Long courseId;
    
    private String semester;
    
    private String grade;
}
