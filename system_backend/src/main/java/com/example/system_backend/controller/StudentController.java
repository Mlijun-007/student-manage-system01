package com.example.system_backend.controller;

import com.example.system_backend.dto.StudentDTO;
import com.example.system_backend.dto.ResponseDTO;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
            schema = @Schema(implementation = ResponseDTO.class)
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
            examples = @ExampleObject(value = "{\"code\": 400, \"message\": \"性别无效，必须是男/女\", \"data\": null}")
        )
    )
    @PostMapping
    public ResponseEntity<ResponseDTO<Student>> createStudent(@Valid @RequestBody StudentDTO studentDTO) {
        Student student = studentService.createStudent(studentDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ResponseDTO.success("学生创建成功", student));
    }
    
    // 获取学生详情
    @Operation(summary = "获取学生详情", description = "根据ID获取学生的详细信息")
    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO<Student>> getStudentById(@PathVariable Long id) {
        Student student = studentService.getStudentById(id)
                .orElseThrow(() -> new NoSuchElementException("学生不存在，ID: " + id));
        return ResponseEntity.ok(ResponseDTO.success("获取学生成功", student));
    }
    
    // 获取学生列表（分页）
    @Operation(summary = "获取学生列表", description = "分页获取所有学生信息")
    @GetMapping
    public ResponseEntity<ResponseDTO<Page<Student>>> getAllStudents(Pageable pageable) {
        Page<Student> students = studentService.getAllStudents(pageable);
        return ResponseEntity.ok(ResponseDTO.success("获取学生列表成功", students));
    }
    
    // 更新学生信息
    @Operation(summary = "更新学生信息", description = "根据ID更新学生的详细信息")
    @PutMapping("/{id}")
    public ResponseEntity<ResponseDTO<Student>> updateStudent(@PathVariable Long id, @Valid @RequestBody StudentDTO studentDTO) {
        Student student = studentService.updateStudent(id, studentDTO);
        return ResponseEntity.ok(ResponseDTO.success("学生更新成功", student));
    }
    
    // 删除学生
    @Operation(summary = "删除学生", description = "根据ID删除学生信息")
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO<Void>> deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.ok(ResponseDTO.success("学生删除成功", null));
    }
}
