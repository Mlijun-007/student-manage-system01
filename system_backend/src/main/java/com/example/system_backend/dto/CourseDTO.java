package com.example.system_backend.dto;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.DecimalMin;
import java.math.BigDecimal;

@Data
public class CourseDTO {
    private Long id;
    
    @NotBlank(message = "课程名称不能为空")
    @Size(min = 2, max = 100, message = "课程名称长度必须在2到100个字符之间")
    private String name;
    
    @NotBlank(message = "课程代码不能为空")
    @Size(min = 3, max = 20, message = "课程代码长度必须在3到20个字符之间")
    private String courseCode;
    
    @NotBlank(message = "授课教师不能为空")
    @Size(min = 2, max = 50, message = "授课教师姓名长度必须在2到50个字符之间")
    private String teacher;
    
    @DecimalMin(value = "1.0", message = "课程学分必须大于等于1.0")
    private BigDecimal credit;
    
    @Size(max = 200, message = "课程描述长度不能超过200个字符")
    private String description;
    
    @DecimalMin(value = "0.0", message = "课程价格必须大于等于0.0")
    private BigDecimal price;
    
    private Integer classHour; // 课时
}
