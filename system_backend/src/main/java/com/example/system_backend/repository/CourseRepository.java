package com.example.system_backend.repository;

import com.example.system_backend.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    // 移除了与courseCode相关的方法，因为courseCode字段已从Course实体中删除
}
