package com.example.system_backend.entity;

import lombok.Data;
import jakarta.persistence.*;

@Entity
@Table(name = "enrollments")
@Data
public class Enrollment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;
    
    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;
    
    @Column(name = "semester", length = 20)
    private String semester;
    
    @Column(name = "grade", length = 10)
    private String grade;
}
