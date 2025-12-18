package com.example.system_backend.repository;

import com.example.system_backend.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    // 移除了与studentId和email相关的方法，因为这些字段已从Student实体中删除
}
