package com.example.system_backend.service;

import com.example.system_backend.dto.EnrollmentDTO;
import com.example.system_backend.entity.Enrollment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

public interface EnrollmentService {
    Enrollment createEnrollment(EnrollmentDTO enrollmentDTO);
    Optional<Enrollment> getEnrollmentById(Long id);
    List<Enrollment> getAllEnrollments();
    Page<Enrollment> getAllEnrollments(Pageable pageable);
    List<Enrollment> getEnrollmentsByStudentId(Long studentId);
    Page<Enrollment> getEnrollmentsByStudentId(Long studentId, Pageable pageable);
    List<Enrollment> getEnrollmentsByCourseId(Long courseId);
    Page<Enrollment> getEnrollmentsByCourseId(Long courseId, Pageable pageable);
    Enrollment updateEnrollment(Long id, EnrollmentDTO enrollmentDTO);
    void deleteEnrollment(Long id);
}
