package com.example.system_backend.service;

import com.example.system_backend.dto.ClassDTO;
import com.example.system_backend.entity.ClassEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

public interface ClassService {
    ClassEntity createClass(ClassDTO classDTO);
    Optional<ClassEntity> getClassById(Long id);
    List<ClassEntity> getAllClasses();
    Page<ClassEntity> getAllClasses(Pageable pageable);
    ClassEntity updateClass(Long id, ClassDTO classDTO);
    void deleteClass(Long id);
}
