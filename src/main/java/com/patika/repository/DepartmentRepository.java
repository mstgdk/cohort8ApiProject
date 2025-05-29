package com.patika.repository;

import com.patika.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository // opsiyonel
public interface DepartmentRepository extends JpaRepository<Department,Long> {
    boolean existsByDepartmentCode(String departmenCode);
}
