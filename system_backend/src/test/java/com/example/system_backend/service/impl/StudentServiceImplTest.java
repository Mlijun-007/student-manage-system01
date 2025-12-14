package com.example.system_backend.service.impl;

import com.example.system_backend.dto.StudentDTO;
import com.example.system_backend.entity.ClassEntity;
import com.example.system_backend.entity.Student;
import com.example.system_backend.repository.ClassRepository;
import com.example.system_backend.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StudentServiceImplTest {

    @Mock
    private StudentRepository studentRepository;
    
    @Mock
    private ClassRepository classRepository;

    @InjectMocks
    private StudentServiceImpl studentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateStudent_Success() {
        // 准备测试数据
        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setName("张三");
        studentDTO.setStudentId("20230001");
        studentDTO.setEmail("zhangsan@example.com");
        studentDTO.setPhone("13800138000");
        studentDTO.setMajor("计算机科学");
        studentDTO.setClassId(1L);
        studentDTO.setGender("MALE");
        studentDTO.setRegisterTime("2023-09-01");

        Student savedStudent = new Student();
        savedStudent.setId(1L);
        savedStudent.setName(studentDTO.getName());
        savedStudent.setStudentId(studentDTO.getStudentId());
        savedStudent.setEmail(studentDTO.getEmail());
        savedStudent.setPhone(studentDTO.getPhone());
        savedStudent.setMajor(studentDTO.getMajor());
        savedStudent.setGender(Student.Gender.MALE);
        savedStudent.setRegisterTime(LocalDate.parse(studentDTO.getRegisterTime()));

        // 模拟班级Repository
        ClassEntity clazz = new ClassEntity();
        clazz.setId(studentDTO.getClassId());
        clazz.setClassName("计科2301");
        when(classRepository.findById(studentDTO.getClassId())).thenReturn(Optional.of(clazz));
        
        // 模拟学生Repository方法
        when(studentRepository.existsByStudentId(studentDTO.getStudentId())).thenReturn(false);
        when(studentRepository.existsByEmail(studentDTO.getEmail())).thenReturn(false);
        when(studentRepository.save(any(Student.class))).thenReturn(savedStudent);
        
        // 设置学生的班级属性
        savedStudent.setClazz(clazz);

        // 执行测试
        Student result = studentService.createStudent(studentDTO);

        // 验证结果
        assertNotNull(result);
        assertEquals(savedStudent.getId(), result.getId());
        assertEquals(savedStudent.getName(), result.getName());
        verify(studentRepository).existsByStudentId(studentDTO.getStudentId());
        verify(studentRepository).existsByEmail(studentDTO.getEmail());
        verify(studentRepository).save(any(Student.class));
    }

    @Test
    void testCreateStudent_DuplicateStudentId() {
        // 准备测试数据
        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setStudentId("20230001");
        studentDTO.setEmail("zhangsan@example.com");

        // 模拟Repository方法
        when(studentRepository.existsByStudentId(studentDTO.getStudentId())).thenReturn(true);

        // 执行测试并验证异常
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> studentService.createStudent(studentDTO));
        assertEquals("学号" + studentDTO.getStudentId() + "已存在", exception.getMessage());
        verify(studentRepository).existsByStudentId(studentDTO.getStudentId());
        verify(studentRepository, never()).existsByEmail(anyString());
        verify(studentRepository, never()).save(any(Student.class));
    }

    @Test
    void testCreateStudent_DuplicateEmail() {
        // 准备测试数据
        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setStudentId("20230001");
        studentDTO.setEmail("zhangsan@example.com");

        // 模拟Repository方法
        when(studentRepository.existsByStudentId(studentDTO.getStudentId())).thenReturn(false);
        when(studentRepository.existsByEmail(studentDTO.getEmail())).thenReturn(true);

        // 执行测试并验证异常
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> studentService.createStudent(studentDTO));
        assertEquals("邮箱" + studentDTO.getEmail() + "已存在", exception.getMessage());
        verify(studentRepository).existsByStudentId(studentDTO.getStudentId());
        verify(studentRepository).existsByEmail(studentDTO.getEmail());
        verify(studentRepository, never()).save(any(Student.class));
    }

    @Test
    void testGetStudentById_Success() {
        // 准备测试数据
        Long studentId = 1L;
        Student student = new Student();
        student.setId(studentId);
        student.setName("张三");

        // 模拟Repository方法
        when(studentRepository.findById(studentId)).thenReturn(Optional.of(student));

        // 执行测试
        Optional<Student> result = studentService.getStudentById(studentId);

        // 验证结果
        assertTrue(result.isPresent());
        assertEquals(student.getName(), result.get().getName());
        verify(studentRepository).findById(studentId);
    }

    @Test
    void testGetStudentById_NotFound() {
        // 准备测试数据
        Long studentId = 1L;

        // 模拟Repository方法
        when(studentRepository.findById(studentId)).thenReturn(Optional.empty());

        // 执行测试
        Optional<Student> result = studentService.getStudentById(studentId);

        // 验证结果
        assertFalse(result.isPresent());
        verify(studentRepository).findById(studentId);
    }

    @Test
    void testGetAllStudents() {
        // 准备测试数据
        List<Student> studentList = new ArrayList<>();
        Student student1 = new Student();
        student1.setId(1L);
        student1.setName("张三");
        studentList.add(student1);

        Student student2 = new Student();
        student2.setId(2L);
        student2.setName("李四");
        studentList.add(student2);

        // 模拟Repository方法
        when(studentRepository.findAll()).thenReturn(studentList);

        // 执行测试
        List<Student> result = studentService.getAllStudents();

        // 验证结果
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(studentRepository).findAll();
    }

    @Test
    void testUpdateStudent_Success() {
        // 准备测试数据
        Long studentId = 1L;
        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setName("张三更新");
        studentDTO.setStudentId("20230001");
        studentDTO.setEmail("zhangsan_updated@example.com");
        studentDTO.setPhone("13800138000");
        studentDTO.setMajor("计算机科学");
        studentDTO.setClassId(1L);
        studentDTO.setGender("MALE");
        studentDTO.setRegisterTime("2023-09-01");

        Student existingStudent = new Student();
        existingStudent.setId(studentId);
        existingStudent.setName("张三");
        existingStudent.setStudentId("20230001");
        existingStudent.setEmail("zhangsan@example.com");
        existingStudent.setPhone("13800138000");
        existingStudent.setMajor("计算机科学");
        existingStudent.setGender(Student.Gender.MALE);
        existingStudent.setRegisterTime(LocalDate.parse(studentDTO.getRegisterTime()));

        Student updatedStudent = new Student();
        updatedStudent.setId(studentId);
        updatedStudent.setName(studentDTO.getName());
        updatedStudent.setStudentId(studentDTO.getStudentId());
        updatedStudent.setEmail(studentDTO.getEmail());
        updatedStudent.setPhone(studentDTO.getPhone());
        updatedStudent.setMajor(studentDTO.getMajor());
        updatedStudent.setGender(Student.Gender.MALE);
        updatedStudent.setRegisterTime(LocalDate.parse(studentDTO.getRegisterTime()));

        // 模拟班级Repository
        ClassEntity clazz = new ClassEntity();
        clazz.setId(studentDTO.getClassId());
        clazz.setClassName("计科2301");
        when(classRepository.findById(studentDTO.getClassId())).thenReturn(Optional.of(clazz));
        
        // 模拟学生Repository方法
        when(studentRepository.findById(studentId)).thenReturn(Optional.of(existingStudent));
        when(studentRepository.existsByStudentId(studentDTO.getStudentId())).thenReturn(false);
        when(studentRepository.existsByEmail(studentDTO.getEmail())).thenReturn(false);
        when(studentRepository.save(any(Student.class))).thenReturn(updatedStudent);
        
        // 设置学生的班级属性
        existingStudent.setClazz(clazz);
        updatedStudent.setClazz(clazz);

        // 执行测试
        Student result = studentService.updateStudent(studentId, studentDTO);

        // 验证结果
        assertNotNull(result);
        assertEquals(updatedStudent.getName(), result.getName());
        assertEquals(updatedStudent.getEmail(), result.getEmail());
        verify(studentRepository).findById(studentId);
        // 学号没有变化，所以existsByStudentId不会被调用
        verify(studentRepository, never()).existsByStudentId(studentDTO.getStudentId());
        verify(studentRepository).existsByEmail(studentDTO.getEmail());
        verify(studentRepository).save(any(Student.class));
    }

    @Test
    void testUpdateStudent_NotFound() {
        // 准备测试数据
        Long studentId = 1L;
        StudentDTO studentDTO = new StudentDTO();

        // 模拟Repository方法
        when(studentRepository.findById(studentId)).thenReturn(Optional.empty());

        // 执行测试并验证异常
        NoSuchElementException exception = assertThrows(NoSuchElementException.class,
                () -> studentService.updateStudent(studentId, studentDTO));
        assertEquals("学生不存在，ID: " + studentId, exception.getMessage());
        verify(studentRepository).findById(studentId);
        verify(studentRepository, never()).save(any(Student.class));
    }

    @Test
    void testUpdateStudent_DuplicateStudentId() {
        // 准备测试数据
        Long studentId = 1L;
        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setStudentId("20230002"); // 新学号
        studentDTO.setEmail("zhangsan@example.com");

        Student existingStudent = new Student();
        existingStudent.setId(studentId);
        existingStudent.setStudentId("20230001"); // 旧学号不同
        existingStudent.setEmail("zhangsan@example.com");

        // 模拟Repository方法
        when(studentRepository.findById(studentId)).thenReturn(Optional.of(existingStudent));
        when(studentRepository.existsByStudentId(studentDTO.getStudentId())).thenReturn(true);

        // 执行测试并验证异常
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> studentService.updateStudent(studentId, studentDTO));
        assertEquals("学号" + studentDTO.getStudentId() + "已存在", exception.getMessage());
        verify(studentRepository).findById(studentId);
        verify(studentRepository).existsByStudentId(studentDTO.getStudentId());
        verify(studentRepository, never()).save(any(Student.class));
    }

    @Test
    void testUpdateStudent_DuplicateEmail() {
        // 准备测试数据
        Long studentId = 1L;
        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setStudentId("20230001");
        studentDTO.setEmail("lisi@example.com"); // 新邮箱

        Student existingStudent = new Student();
        existingStudent.setId(studentId);
        existingStudent.setStudentId("20230001");
        existingStudent.setEmail("zhangsan@example.com"); // 旧邮箱不同

        // 模拟Repository方法
        when(studentRepository.findById(studentId)).thenReturn(Optional.of(existingStudent));
        when(studentRepository.existsByEmail(studentDTO.getEmail())).thenReturn(true);

        // 执行测试并验证异常
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> studentService.updateStudent(studentId, studentDTO));
        assertEquals("邮箱" + studentDTO.getEmail() + "已存在", exception.getMessage());
        verify(studentRepository).findById(studentId);
        verify(studentRepository).existsByEmail(studentDTO.getEmail());
        verify(studentRepository, never()).save(any(Student.class));
    }

    @Test
    void testDeleteStudent_Success() {
        // 准备测试数据
        Long studentId = 1L;

        // 模拟Repository方法
        when(studentRepository.existsById(studentId)).thenReturn(true);
        doNothing().when(studentRepository).deleteById(studentId);

        // 执行测试
        studentService.deleteStudent(studentId);

        // 验证结果
        verify(studentRepository).existsById(studentId);
        verify(studentRepository).deleteById(studentId);
    }

    @Test
    void testDeleteStudent_NotFound() {
        // 准备测试数据
        Long studentId = 1L;

        // 模拟Repository方法
        when(studentRepository.existsById(studentId)).thenReturn(false);

        // 执行测试并验证异常
        NoSuchElementException exception = assertThrows(NoSuchElementException.class,
                () -> studentService.deleteStudent(studentId));
        assertEquals("学生不存在，ID: " + studentId, exception.getMessage());
        verify(studentRepository).existsById(studentId);
        verify(studentRepository, never()).deleteById(studentId);
    }
}
