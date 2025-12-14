package com.example.system_backend.service.impl;

import com.example.system_backend.dto.EnrollmentDTO;
import com.example.system_backend.entity.Enrollment;
import com.example.system_backend.entity.Student;
import com.example.system_backend.entity.Course;
import com.example.system_backend.repository.EnrollmentRepository;
import com.example.system_backend.repository.StudentRepository;
import com.example.system_backend.repository.CourseRepository;
import com.example.system_backend.service.EnrollmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional
public class EnrollmentServiceImpl implements EnrollmentService {
    
    @Autowired
    private EnrollmentRepository enrollmentRepository;
    
    @Autowired
    private StudentRepository studentRepository;
    
    @Autowired
    private CourseRepository courseRepository;
    
    @Override
    public Enrollment createEnrollment(EnrollmentDTO enrollmentDTO) {
        // 二次验证：检查学生是否存在
        Student student = studentRepository.findById(enrollmentDTO.getStudentId())
                .orElseThrow(() -> new NoSuchElementException("学生不存在，ID: " + enrollmentDTO.getStudentId()));
        
        // 二次验证：检查课程是否存在
        Course course = courseRepository.findById(enrollmentDTO.getCourseId())
                .orElseThrow(() -> new NoSuchElementException("课程不存在，ID: " + enrollmentDTO.getCourseId()));
        
        // 二次验证：检查学生是否已经选修了该课程（避免重复选课）
        if (enrollmentRepository.existsByStudentIdAndCourseId(student.getId(), course.getId())) {
            throw new IllegalArgumentException("学生已选修该课程");
        }
        
        // 将DTO转换为Entity
        Enrollment enrollment = new Enrollment();
        enrollment.setStudent(student);
        enrollment.setCourse(course);
        enrollment.setSemester(enrollmentDTO.getSemester());
        enrollment.setGrade(enrollmentDTO.getGrade());
        
        return enrollmentRepository.save(enrollment);
    }
    
    @Override
    public Optional<Enrollment> getEnrollmentById(Long id) {
        return enrollmentRepository.findById(id);
    }
    
    @Override
    public List<Enrollment> getAllEnrollments() {
        return enrollmentRepository.findAll();
    }
    
    @Override
    public Page<Enrollment> getAllEnrollments(Pageable pageable) {
        return enrollmentRepository.findAll(pageable);
    }
    
    @Override
    public List<Enrollment> getEnrollmentsByStudentId(Long studentId) {
        // 二次验证：检查学生是否存在
        if (!studentRepository.existsById(studentId)) {
            throw new NoSuchElementException("学生不存在，ID: " + studentId);
        }
        
        return enrollmentRepository.findByStudentId(studentId);
    }
    
    @Override
    public Page<Enrollment> getEnrollmentsByStudentId(Long studentId, Pageable pageable) {
        // 二次验证：检查学生是否存在
        if (!studentRepository.existsById(studentId)) {
            throw new NoSuchElementException("学生不存在，ID: " + studentId);
        }
        
        return enrollmentRepository.findByStudentId(studentId, pageable);
    }
    
    @Override
    public List<Enrollment> getEnrollmentsByCourseId(Long courseId) {
        // 二次验证：检查课程是否存在
        if (!courseRepository.existsById(courseId)) {
            throw new NoSuchElementException("课程不存在，ID: " + courseId);
        }
        
        return enrollmentRepository.findByCourseId(courseId);
    }
    
    @Override
    public Page<Enrollment> getEnrollmentsByCourseId(Long courseId, Pageable pageable) {
        // 二次验证：检查课程是否存在
        if (!courseRepository.existsById(courseId)) {
            throw new NoSuchElementException("课程不存在，ID: " + courseId);
        }
        
        return enrollmentRepository.findByCourseId(courseId, pageable);
    }
    
    @Override
    public Enrollment updateEnrollment(Long id, EnrollmentDTO enrollmentDTO) {
        // 二次验证：检查选课记录是否存在
        Enrollment existingEnrollment = enrollmentRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("选课记录不存在，ID: " + id));
        
        // 二次验证：检查学生是否存在
        Student student = studentRepository.findById(enrollmentDTO.getStudentId())
                .orElseThrow(() -> new NoSuchElementException("学生不存在，ID: " + enrollmentDTO.getStudentId()));
        
        // 二次验证：检查课程是否存在
        Course course = courseRepository.findById(enrollmentDTO.getCourseId())
                .orElseThrow(() -> new NoSuchElementException("课程不存在，ID: " + enrollmentDTO.getCourseId()));
        
        // 二次验证：检查是否存在其他记录使用相同的学生和课程组合
        if (!existingEnrollment.getStudent().getId().equals(student.getId()) || 
            !existingEnrollment.getCourse().getId().equals(course.getId())) {
            if (enrollmentRepository.existsByStudentIdAndCourseId(student.getId(), course.getId())) {
                throw new IllegalArgumentException("学生已选修该课程");
            }
        }
        
        // 更新选课信息
        existingEnrollment.setStudent(student);
        existingEnrollment.setCourse(course);
        existingEnrollment.setSemester(enrollmentDTO.getSemester());
        existingEnrollment.setGrade(enrollmentDTO.getGrade());
        
        return enrollmentRepository.save(existingEnrollment);
    }
    
    @Override
    public void deleteEnrollment(Long id) {
        // 二次验证：检查选课记录是否存在
        if (!enrollmentRepository.existsById(id)) {
            throw new NoSuchElementException("选课记录不存在，ID: " + id);
        }
        
        enrollmentRepository.deleteById(id);
    }
}
