package com.example.system_backend.dto;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.NotNull;

@Data
public class StudentDTO {
    private Long id;
    
    @NotBlank(message = "学生姓名不能为空")
    @Size(min = 2, max = 50, message = "学生姓名长度必须在2到50个字符之间")
    private String name;
    
    @NotBlank(message = "学号不能为空")
    @Size(min = 8, max = 20, message = "学号长度必须在8到20个字符之间")
    private String studentId;
    
    @NotBlank(message = "邮箱不能为空")
    @Email(message = "邮箱格式不正确")
    private String email;
    
    @NotBlank(message = "电话号码不能为空")
    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "电话号码格式不正确")
    private String phone;
    
    @NotBlank(message = "专业不能为空")
    @Size(min = 2, max = 50, message = "专业长度必须在2到50个字符之间")
    private String major;
    
    @NotNull(message = "班级ID不能为空")
    private Long classId;
    
    @NotBlank(message = "性别不能为空")
    @Pattern(regexp = "^[男女MALEFEMALE]$", message = "性别无效，必须是男/女")
    private String gender;
    
    private String registerTime; // 注册时间，格式：YYYY-MM-DD
    
    @Size(max = 200, message = "备注长度不能超过200个字符")
    private String remark;
}
