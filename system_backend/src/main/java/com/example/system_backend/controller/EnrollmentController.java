package com.example.system_backend.controller;

import com.example.system_backend.dto.EnrollmentDTO;
import com.example.system_backend.dto.ResponseDTO;
import com.example.system_backend.entity.Enrollment;
import com.example.system_backend.service.EnrollmentService;
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

@Tag(name = "选课管理", description = "选课相关API接口")
@RestController
@RequestMapping("/api/enrollments")
public class EnrollmentController {
    
    @Autowired
    private EnrollmentService enrollmentService;
    
    // 创建选课记录
    @Operation(summary = "创建选课记录", description = "创建新的选课记录")
    @PostMapping
    public ResponseEntity<ResponseDTO<Enrollment>> createEnrollment(@Valid @RequestBody EnrollmentDTO enrollmentDTO) {
        Enrollment enrollment = enrollmentService.createEnrollment(enrollmentDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ResponseDTO.success("选课成功", enrollment));
    }
    
    // 获取选课详情
    @Operation(summary = "获取选课记录详情", description = "根据ID获取选课记录的详细信息")
    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO<Enrollment>> getEnrollmentById(@PathVariable Long id) {
        Enrollment enrollment = enrollmentService.getEnrollmentById(id)
                .orElseThrow(() -> new NoSuchElementException("选课记录不存在，ID: " + id));
        return ResponseEntity.ok(ResponseDTO.success("获取选课记录成功", enrollment));
    }
    
    // 获取选课列表（分页）
    @Operation(summary = "获取选课记录列表", description = "分页获取所有选课记录")
    @GetMapping
    public ResponseEntity<ResponseDTO<Page<Enrollment>>> getAllEnrollments(Pageable pageable) {
        Page<Enrollment> enrollments = enrollmentService.getAllEnrollments(pageable);
        return ResponseEntity.ok(ResponseDTO.success("获取选课列表成功", enrollments));
    }
    
    // 根据学生ID获取选课记录
    @Operation(summary = "获取学生选课记录", description = "根据学生ID分页获取选课记录")
    @GetMapping("/student/{studentId}")
    public ResponseEntity<ResponseDTO<Page<Enrollment>>> getEnrollmentsByStudentId(@PathVariable Long studentId, Pageable pageable) {
        Page<Enrollment> enrollments = enrollmentService.getEnrollmentsByStudentId(studentId, pageable);
        return ResponseEntity.ok(ResponseDTO.success("获取学生选课记录成功", enrollments));
    }
    
    // 根据课程ID获取选课记录
    @Operation(summary = "获取课程选课记录", description = "根据课程ID分页获取选课记录")
    @GetMapping("/course/{courseId}")
    public ResponseEntity<ResponseDTO<Page<Enrollment>>> getEnrollmentsByCourseId(@PathVariable Long courseId, Pageable pageable) {
        Page<Enrollment> enrollments = enrollmentService.getEnrollmentsByCourseId(courseId, pageable);
        return ResponseEntity.ok(ResponseDTO.success("获取课程选课记录成功", enrollments));
    }
    
    // 删除选课记录
    @Operation(summary = "删除选课记录", description = "根据ID删除选课记录")
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO<Void>> deleteEnrollment(@PathVariable Long id) {
        enrollmentService.deleteEnrollment(id);
        return ResponseEntity.ok(ResponseDTO.success("退课成功", null));
    }
}
