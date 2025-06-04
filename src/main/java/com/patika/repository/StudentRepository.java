package com.patika.repository;

import com.patika.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student,Long> {

    boolean existsByConnectionId(Long id);
}
