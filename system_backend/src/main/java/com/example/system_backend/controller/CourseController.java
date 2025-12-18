package com.example.system_backend.controller;

import com.example.system_backend.dto.CourseDTO;
import com.example.system_backend.entity.Course;
import com.example.system_backend.service.CourseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import java.util.NoSuchElementException;

@Tag(name = "课程管理", description = "课程相关API接口")
@RestController
@RequestMapping("/api/courses")
public class CourseController {
    
    @Autowired
    private CourseService courseService;
    
    // 创建课程
    @Operation(summary = "创建课程", description = "创建新的课程信息")
    @PostMapping
    public ResponseEntity<Course> createCourse(@Valid @RequestBody CourseDTO courseDTO) {
        Course course = courseService.createCourse(courseDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(course);
    }
    
    // 获取课程详情
    @Operation(summary = "获取课程详情", description = "根据ID获取课程的详细信息")
    @GetMapping("/{id}")
    public ResponseEntity<Course> getCourseById(@PathVariable Long id) {
        Course course = courseService.getCourseById(id)
                .orElseThrow(() -> new NoSuchElementException("课程不存在，ID: " + id));
        return ResponseEntity.ok(course);
    }
    
    // 获取课程列表
    @Operation(summary = "获取课程列表", description = "获取所有课程信息列表")
    @GetMapping
    public ResponseEntity<List<Course>> getAllCourses() {
        List<Course> courses = courseService.getAllCourses();
        return ResponseEntity.ok(courses);
    }
    
    // 更新课程信息
    @Operation(summary = "更新课程信息", description = "根据ID更新课程的详细信息")
    @PutMapping("/{id}")
    public ResponseEntity<Course> updateCourse(@PathVariable Long id, @Valid @RequestBody CourseDTO courseDTO) {
        Course course = courseService.updateCourse(id, courseDTO);
        return ResponseEntity.ok(course);
    }
    
    // 删除课程
    @Operation(summary = "删除课程", description = "根据ID删除课程信息")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable Long id) {
        courseService.deleteCourse(id);
        return ResponseEntity.ok().build();
    }
}
