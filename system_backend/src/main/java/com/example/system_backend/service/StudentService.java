package com.example.system_backend.service;

import com.example.system_backend.dto.StudentDTO;
import com.example.system_backend.entity.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

public interface StudentService {
    Student createStudent(StudentDTO studentDTO);
    Optional<Student> getStudentById(Long id);
    List<Student> getAllStudents();
    Page<Student> getAllStudents(Pageable pageable);
    Student updateStudent(Long id, StudentDTO studentDTO);
    void deleteStudent(Long id);
}
