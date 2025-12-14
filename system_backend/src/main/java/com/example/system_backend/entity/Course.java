package com.example.system_backend.entity;

import lombok.Data;
import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "courses")
@Data
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "name", nullable = false, length = 100)
    private String name;
    
    @Column(name = "course_code", nullable = false, unique = true, length = 20)
    private String courseCode;
    
    @Column(name = "teacher", nullable = false, length = 50)
    private String teacher;
    
    @Column(name = "credit", nullable = false)
    private BigDecimal credit;
    
    @Column(name = "description", length = 200)
    private String description;
    
    @Column(name = "price", nullable = false)
    private BigDecimal price;
    
    @Column(name = "class_hour", nullable = false)
    private Integer classHour; // 课时
}
