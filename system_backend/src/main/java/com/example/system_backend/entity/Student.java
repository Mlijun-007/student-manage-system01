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
    
    @Column(name = "phone", nullable = false, length = 20)
    private String phone;
    
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
        MALE("男"),   // 男
        FEMALE("女"),  // 女
        OTHER("其他");  // 其他
        
        private final String displayName;
        
        // 构造器
        Gender(String displayName) {
            this.displayName = displayName;
        }
        
        // 获取显示名称
        public String getDisplayName() {
            return displayName;
        }
    }
}
