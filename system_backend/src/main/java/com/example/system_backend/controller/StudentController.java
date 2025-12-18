package com.example.system_backend.controller;

import com.example.system_backend.dto.StudentDTO;
import com.example.system_backend.entity.Student;
import com.example.system_backend.service.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import java.util.NoSuchElementException;

@Tag(name = "学生管理", description = "学生相关API接口")
@RestController
@RequestMapping("/api/students")
public class StudentController {
    
    @Autowired
    private StudentService studentService;
    
    // 创建学生
    @Operation(summary = "添加学员", description = "创建新学生，包含完整的参数验证")
    @ApiResponse(
        responseCode = "201",
        description = "学生创建成功",
        content = @Content(
            schema = @Schema(implementation = Student.class)
        )
    )
    @ApiResponse(
        responseCode = "400",
        description = "验证错误示例",
        content = @Content(
            examples = @ExampleObject(value = "{\"code\": 400, \"message\": \"手机号码格式不正确\", \"data\": null}")
        )
    )
    @ApiResponse(
        responseCode = "400",
        description = "验证错误示例",
        content = @Content(
            examples = @ExampleObject(value = "{\"code\": 400, \"message\": \"性别无效，必须是男/女/其他\", \"data\": null}")
        )
    )
    @PostMapping
    public ResponseEntity<Student> createStudent(@Valid @RequestBody StudentDTO studentDTO) {
        Student student = studentService.createStudent(studentDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(student);
    }
    
    // 获取学生详情
    @Operation(summary = "获取学生详情", description = "根据ID获取学生的详细信息")
    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable Long id) {
        Student student = studentService.getStudentById(id)
                .orElseThrow(() -> new NoSuchElementException("学生不存在，ID: " + id));
        return ResponseEntity.ok(student);
    }
    
    // 获取学生列表（直接返回列表，不使用分页，以匹配前端需求）
    @Operation(summary = "获取学生列表", description = "获取所有学生信息列表")
    @GetMapping
     public ResponseEntity<List<Student>> getAllStudents() {
        List<Student> students = studentService.getAllStudents();
        return ResponseEntity.ok(students);
    }
    
    // 更新学生信息
    @Operation(summary = "更新学生信息", description = "根据ID更新学生的详细信息")
    @PutMapping("/{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable Long id, @Valid @RequestBody StudentDTO studentDTO) {
        Student student = studentService.updateStudent(id, studentDTO);
        return ResponseEntity.ok(student);
    }
    
    // 删除学生
    @Operation(summary = "删除学生", description = "根据ID删除学生信息")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.ok().build();
    }
}
