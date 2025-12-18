package com.example.system_backend.entity;

import lombok.Data;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "classes")
@Data
public class ClassEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "class_name", nullable = false, length = 100)
    private String className;
    
    @Column(name = "status", nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private ClassStatus status;
    
    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;
    
    @Column(name = "start_date", nullable = false)
    private LocalDate startDate; // 新增：开班时间
    
    @Column(name = "teacher", nullable = false, length = 50)
    private String teacher; // 新增：授课老师
    
    // 班级状态枚举
    public enum ClassStatus {
        NOT_STARTED("未开班"), // 未开班
        IN_PROGRESS("已开班"), // 已开班
        COMPLETED("已结课");   // 已结课
        
        private final String displayName;
        
        ClassStatus(String displayName) {
            this.displayName = displayName;
        }
        
        public String getDisplayName() {
            return displayName;
        }
    }
}
