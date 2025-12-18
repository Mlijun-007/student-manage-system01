package com.example.system_backend.service.impl;

import com.example.system_backend.dto.CourseDTO;
import com.example.system_backend.entity.Course;
import com.example.system_backend.repository.CourseRepository;
import com.example.system_backend.service.CourseService;
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
public class CourseServiceImpl implements CourseService {
    
    @Autowired
    private CourseRepository courseRepository;
    
    @Override
    public Course createCourse(CourseDTO courseDTO) {
        // 将DTO转换为Entity
        Course course = new Course();
        course.setCourseName(courseDTO.getCourseName());
        course.setDescription(courseDTO.getDescription());
        course.setPrice(courseDTO.getPrice());
        course.setClassHour(courseDTO.getClassHour());
        
        return courseRepository.save(course);
    }
    
    @Override
    public Optional<Course> getCourseById(Long id) {
        return courseRepository.findById(id);
    }
    
    @Override
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }
    
    @Override
    public Page<Course> getAllCourses(Pageable pageable) {
        return courseRepository.findAll(pageable);
    }
    
    @Override
    public Course updateCourse(Long id, CourseDTO courseDTO) {
        // 二次验证：检查课程是否存在
        Course existingCourse = courseRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("课程不存在，ID: " + id));
        
        // 更新课程信息
        existingCourse.setCourseName(courseDTO.getCourseName());
        existingCourse.setDescription(courseDTO.getDescription());
        existingCourse.setPrice(courseDTO.getPrice());
        existingCourse.setClassHour(courseDTO.getClassHour());
        
        return courseRepository.save(existingCourse);
    }
    
    @Override
    public void deleteCourse(Long id) {
        // 二次验证：检查课程是否存在
        if (!courseRepository.existsById(id)) {
            throw new NoSuchElementException("课程不存在，ID: " + id);
        }
        
        courseRepository.deleteById(id);
    }
}
