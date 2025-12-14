package com.example.system_backend.dto;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Data
public class ClassDTO {
    private Long id;
    
    @NotBlank(message = "班级名称不能为空")
    @Size(min = 2, max = 100, message = "班级名称长度必须在2到100个字符之间")
    private String className;
    
    @NotBlank(message = "班级状态不能为空")
    private String status;
    
    @NotNull(message = "课程ID不能为空")
    private Long courseId;
    
    private String startDate; // 开课日期，格式：YYYY-MM-DD
    
    private String teacher; // 授课教师
}
