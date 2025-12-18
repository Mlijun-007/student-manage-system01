package com.example.system_backend.dto;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class CourseDTO {
    private Long id;
    
    @NotBlank(message = "课程名称不能为空")
    @Size(min = 2, max = 100, message = "课程名称长度必须在2到100个字符之间")
    private String courseName;
    
    @Size(max = 200, message = "课程描述长度不能超过200个字符")
    private String description;
    
    @NotNull(message = "课时总数不能为空")
    private Integer classHour; // 课时
    
    @NotNull(message = "课程价格不能为空")
    @DecimalMin(value = "0.0", message = "课程价格必须大于等于0.0")
    private BigDecimal price;
}
