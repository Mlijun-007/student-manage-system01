package com.example.system_backend.service.impl;

import com.example.system_backend.dto.StudentDTO;
import com.example.system_backend.entity.ClassEntity;
import com.example.system_backend.entity.Student;
import com.example.system_backend.repository.ClassRepository;
import com.example.system_backend.repository.StudentRepository;
import com.example.system_backend.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.NoSuchElementException;
import java.util.regex.Pattern;

@Service
@Transactional
public class StudentServiceImpl implements StudentService {
    
    @Autowired
    private StudentRepository studentRepository;
    
    @Autowired
    private ClassRepository classRepository;
    
    @Override
    public Student createStudent(StudentDTO studentDTO) {
        // 二次验证：手机号码格式校验（兜底校验）
        Pattern phonePattern = Pattern.compile("^1[3-9]\\d{9}$");
        if (!phonePattern.matcher(studentDTO.getPhone()).matches()) {
            throw new IllegalArgumentException("手机号码格式不正确");
        }
        
        // 二次验证：检查性别是否为有效的枚举值
        Student.Gender gender;
        try {
            gender = Student.Gender.valueOf(studentDTO.getGender().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("性别无效，必须是男/女/其他");
        }
        
        // 二次验证：检查班级ID是否存在
        ClassEntity clazz = classRepository.findById(studentDTO.getClassId())
                .orElseThrow(() -> new NoSuchElementException("班级不存在，ID: " + studentDTO.getClassId()));
        
        // 二次验证：注册时间格式转换
        LocalDate registerTime;
        try {
            registerTime = LocalDate.parse(studentDTO.getRegisterTime());
        } catch (Exception e) {
            throw new IllegalArgumentException("注册时间格式不正确，必须为YYYY-MM-DD");
        }
        
        // 将DTO转换为Entity
        Student student = new Student();
        student.setName(studentDTO.getName());
        student.setPhone(studentDTO.getPhone());
        student.setClazz(clazz);
        student.setGender(gender);
        student.setRegisterTime(registerTime);
        student.setRemark(studentDTO.getRemark());
        
        return studentRepository.save(student);
    }
    
    @Override
    public Optional<Student> getStudentById(Long id) {
        return studentRepository.findById(id);
    }
    
    @Override
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }
    
    @Override
    public Page<Student> getAllStudents(Pageable pageable) {
        return studentRepository.findAll(pageable);
    }
    
    @Override
    public Student updateStudent(Long id, StudentDTO studentDTO) {
        // 二次验证：检查学生是否存在
        Student existingStudent = studentRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("学生不存在，ID: " + id));
        
        // 二次验证：手机号码格式校验（兜底校验）
        Pattern phonePattern = Pattern.compile("^1[3-9]\\d{9}$");
        if (!phonePattern.matcher(studentDTO.getPhone()).matches()) {
            throw new IllegalArgumentException("手机号码格式不正确");
        }
        
        // 二次验证：检查性别是否为有效的枚举值
        Student.Gender gender;
        try {
            gender = Student.Gender.valueOf(studentDTO.getGender().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("性别无效，必须是男/女/其他");
        }
        
        // 二次验证：检查班级ID是否存在
        ClassEntity clazz = classRepository.findById(studentDTO.getClassId())
                .orElseThrow(() -> new NoSuchElementException("班级不存在，ID: " + studentDTO.getClassId()));
        
        // 二次验证：注册时间格式转换
        LocalDate registerTime;
        try {
            registerTime = LocalDate.parse(studentDTO.getRegisterTime());
        } catch (Exception e) {
            throw new IllegalArgumentException("注册时间格式不正确，必须为YYYY-MM-DD");
        }
        
        // 更新学生信息
        existingStudent.setName(studentDTO.getName());
        existingStudent.setPhone(studentDTO.getPhone());
        existingStudent.setClazz(clazz);
        existingStudent.setGender(gender);
        existingStudent.setRegisterTime(registerTime);
        existingStudent.setRemark(studentDTO.getRemark());
        
        return studentRepository.save(existingStudent);
    }
    
    @Override
    public void deleteStudent(Long id) {
        // 二次验证：检查学生是否存在
        if (!studentRepository.existsById(id)) {
            throw new NoSuchElementException("学生不存在，ID: " + id);
        }
        
        studentRepository.deleteById(id);
    }
}
