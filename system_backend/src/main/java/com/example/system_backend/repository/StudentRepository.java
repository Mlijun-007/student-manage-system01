package com.example.system_backend.repository;

import com.example.system_backend.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    Optional<Student> findByStudentId(String studentId);
    Optional<Student> findByEmail(String email);
    boolean existsByStudentId(String studentId);
    boolean existsByEmail(String email);
}
