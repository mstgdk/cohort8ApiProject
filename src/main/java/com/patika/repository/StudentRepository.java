package com.patika.repository;

import com.patika.entity.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student,Long> {

    boolean existsByConnectionId(Long id);

    Page<Student> findAllByDepartmentId(Long departmentId, Pageable pageable);
}
