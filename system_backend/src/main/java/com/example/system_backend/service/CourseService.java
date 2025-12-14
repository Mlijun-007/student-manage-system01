package com.example.system_backend.service;

import com.example.system_backend.dto.CourseDTO;
import com.example.system_backend.entity.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

public interface CourseService {
    Course createCourse(CourseDTO courseDTO);
    Optional<Course> getCourseById(Long id);
    List<Course> getAllCourses();
    Page<Course> getAllCourses(Pageable pageable);
    Course updateCourse(Long id, CourseDTO courseDTO);
    void deleteCourse(Long id);
}
