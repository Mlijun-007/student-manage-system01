package com.example.system_backend.entity;

import lombok.Data;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "students")
@Data
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "name", nullable = false, length = 50)
    private String name;
    
    @Column(name = "student_id", nullable = false, unique = true, length = 20)
    private String studentId;
    
    @Column(name = "email", nullable = false, unique = true, length = 100)
    private String email;
    
    @Column(name = "phone", nullable = false, length = 20)
    private String phone;
    
    @Column(name = "major", nullable = false, length = 50)
    private String major;
    
    @ManyToOne
    @JoinColumn(name = "class_id", nullable = false)
    private ClassEntity clazz;
    
    @Column(name = "gender", nullable = false, length = 10)
    @Enumerated(EnumType.STRING)
    private Gender gender;
    
    @Column(name = "register_time", nullable = false)
    private LocalDate registerTime;
    
    @Column(name = "remark", length = 200)
    private String remark;
    
    // 性别枚举
    public enum Gender {
        MALE,   // 男
        FEMALE  // 女
    }
}
