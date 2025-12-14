package com.example.system_backend.service.impl;

import com.example.system_backend.dto.ClassDTO;
import com.example.system_backend.entity.ClassEntity;
import com.example.system_backend.entity.Course;
import com.example.system_backend.repository.ClassRepository;
import com.example.system_backend.repository.CourseRepository;
import com.example.system_backend.service.ClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional
public class ClassServiceImpl implements ClassService {
    
    @Autowired
    private ClassRepository classRepository;
    
    @Autowired
    private CourseRepository courseRepository;
    
    @Override
    public ClassEntity createClass(ClassDTO classDTO) {
        // 二次验证：检查status是否为有效的枚举值
        if (!Arrays.asList("未开班", "已开班", "已结课").contains(classDTO.getStatus())) {
            throw new IllegalArgumentException("状态无效");
        }
        
        // 转换中文状态为枚举值
        ClassEntity.ClassStatus status;
        switch (classDTO.getStatus()) {
            case "未开班":
                status = ClassEntity.ClassStatus.NOT_STARTED;
                break;
            case "已开班":
                status = ClassEntity.ClassStatus.IN_PROGRESS;
                break;
            case "已结课":
                status = ClassEntity.ClassStatus.COMPLETED;
                break;
            default:
                // 这里理论上不会执行，因为上面已经进行了校验
                throw new IllegalArgumentException("状态无效");
        }
        
        // 二次验证：检查course_id是否存在
        Course course = courseRepository.findById(classDTO.getCourseId())
                .orElseThrow(() -> new NoSuchElementException("课程不存在，ID: " + classDTO.getCourseId()));
        
        // 将DTO转换为Entity
        ClassEntity classEntity = new ClassEntity();
        classEntity.setClassName(classDTO.getClassName());
        classEntity.setStatus(status);
        classEntity.setCourse(course);
        
        return classRepository.save(classEntity);
    }
    
    @Override
    public Optional<ClassEntity> getClassById(Long id) {
        return classRepository.findById(id);
    }
    
    @Override
    public List<ClassEntity> getAllClasses() {
        return classRepository.findAll();
    }
    
    @Override
    public Page<ClassEntity> getAllClasses(Pageable pageable) {
        return classRepository.findAll(pageable);
    }
    
    @Override
    public ClassEntity updateClass(Long id, ClassDTO classDTO) {
        // 二次验证：检查班级是否存在
        ClassEntity existingClass = classRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("班级不存在，ID: " + id));
        
        // 二次验证：检查status是否为有效的枚举值
        if (!Arrays.asList("未开班", "已开班", "已结课").contains(classDTO.getStatus())) {
            throw new IllegalArgumentException("状态无效");
        }
        
        // 转换中文状态为枚举值
        ClassEntity.ClassStatus status;
        switch (classDTO.getStatus()) {
            case "未开班":
                status = ClassEntity.ClassStatus.NOT_STARTED;
                break;
            case "已开班":
                status = ClassEntity.ClassStatus.IN_PROGRESS;
                break;
            case "已结课":
                status = ClassEntity.ClassStatus.COMPLETED;
                break;
            default:
                // 这里理论上不会执行，因为上面已经进行了校验
                throw new IllegalArgumentException("状态无效");
        }
        
        // 二次验证：检查course_id是否存在
        Course course = courseRepository.findById(classDTO.getCourseId())
                .orElseThrow(() -> new NoSuchElementException("课程不存在，ID: " + classDTO.getCourseId()));
        
        // 更新班级信息
        existingClass.setClassName(classDTO.getClassName());
        existingClass.setStatus(status);
        existingClass.setCourse(course);
        
        return classRepository.save(existingClass);
    }
    
    @Override
    public void deleteClass(Long id) {
        // 二次验证：检查班级是否存在
        if (!classRepository.existsById(id)) {
            throw new NoSuchElementException("班级不存在，ID: " + id);
        }
        
        classRepository.deleteById(id);
    }
}
