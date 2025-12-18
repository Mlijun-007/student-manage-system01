package com.example.system_backend.controller;

import com.example.system_backend.dto.ClassDTO;
import com.example.system_backend.entity.ClassEntity;
import com.example.system_backend.service.ClassService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import java.util.NoSuchElementException;

@Tag(name = "班级管理", description = "班级相关API接口")
@RestController
@RequestMapping("/api/classes")
public class ClassController {
    
    @Autowired
    private ClassService classService;
    
    // 创建班级
    @Operation(summary = "创建班级", description = "创建新的班级信息")
    @PostMapping
    public ResponseEntity<ClassEntity> createClass(@Valid @RequestBody ClassDTO classDTO) {
        ClassEntity classEntity = classService.createClass(classDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(classEntity);
    }
    
    // 获取班级详情
    @Operation(summary = "获取班级详情", description = "根据ID获取班级的详细信息")
    @GetMapping("/{id}")
    public ResponseEntity<ClassEntity> getClassById(@PathVariable Long id) {
        ClassEntity classEntity = classService.getClassById(id)
                .orElseThrow(() -> new NoSuchElementException("班级不存在，ID: " + id));
        return ResponseEntity.ok(classEntity);
    }
    
    // 获取班级列表
    @Operation(summary = "获取班级列表", description = "获取班级列表，供前端下拉框使用")
    @GetMapping
    public ResponseEntity<List<ClassEntity>> getAllClasses() {
        List<ClassEntity> classes = classService.getAllClasses();
        return ResponseEntity.ok(classes);
    }
    
    // 更新班级信息
    @Operation(summary = "更新班级信息", description = "根据ID更新班级的详细信息")
    @PutMapping("/{id}")
    public ResponseEntity<ClassEntity> updateClass(@PathVariable Long id, @Valid @RequestBody ClassDTO classDTO) {
        ClassEntity classEntity = classService.updateClass(id, classDTO);
        return ResponseEntity.ok(classEntity);
    }
    
    // 删除班级
    @Operation(summary = "删除班级", description = "根据ID删除班级信息")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClass(@PathVariable Long id) {
        classService.deleteClass(id);
        return ResponseEntity.ok().build();
    }
}
