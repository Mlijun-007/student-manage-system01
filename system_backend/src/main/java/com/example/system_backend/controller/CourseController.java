package com.example.system_backend.controller;

import com.example.system_backend.dto.CourseDTO;
import com.example.system_backend.dto.ResponseDTO;
import com.example.system_backend.entity.Course;
import com.example.system_backend.service.CourseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<ResponseDTO<Course>> createCourse(@Valid @RequestBody CourseDTO courseDTO) {
        Course course = courseService.createCourse(courseDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ResponseDTO.success("课程创建成功", course));
    }
    
    // 获取课程详情
    @Operation(summary = "获取课程详情", description = "根据ID获取课程的详细信息")
    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO<Course>> getCourseById(@PathVariable Long id) {
        Course course = courseService.getCourseById(id)
                .orElseThrow(() -> new NoSuchElementException("课程不存在，ID: " + id));
        return ResponseEntity.ok(ResponseDTO.success("获取课程成功", course));
    }
    
    // 获取课程列表（分页）
    @Operation(summary = "获取课程列表", description = "分页获取所有课程信息")
    @GetMapping
    public ResponseEntity<ResponseDTO<Page<Course>>> getAllCourses(Pageable pageable) {
        Page<Course> courses = courseService.getAllCourses(pageable);
        return ResponseEntity.ok(ResponseDTO.success("获取课程列表成功", courses));
    }
    
    // 更新课程信息
    @Operation(summary = "更新课程信息", description = "根据ID更新课程的详细信息")
    @PutMapping("/{id}")
    public ResponseEntity<ResponseDTO<Course>> updateCourse(@PathVariable Long id, @Valid @RequestBody CourseDTO courseDTO) {
        Course course = courseService.updateCourse(id, courseDTO);
        return ResponseEntity.ok(ResponseDTO.success("课程更新成功", course));
    }
    
    // 删除课程
    @Operation(summary = "删除课程", description = "根据ID删除课程信息")
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO<Void>> deleteCourse(@PathVariable Long id) {
        courseService.deleteCourse(id);
        return ResponseEntity.ok(ResponseDTO.success("课程删除成功", null));
    }
}
