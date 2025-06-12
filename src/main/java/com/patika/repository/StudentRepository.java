package com.patika.repository;

import com.patika.entity.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface StudentRepository extends JpaRepository<Student,Long>, JpaSpecificationExecutor<Student> {

    boolean existsByConnectionId(Long id);

    Page<Student> findAllByDepartmentId(Long departmentId, Pageable pageable);


    @Query(value = """
            SELECT COUNT(distinct s.id)
            FROM STUDENT S
            JOIN IMAGE_FILE I ON S.ID=I.STUDENT_ID
            WHERE I.ID = :imageId
            
            """, nativeQuery = true)
    Integer findStudentCountByImageId(@Param("imageId") String id);
}
