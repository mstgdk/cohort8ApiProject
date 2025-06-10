package com.patika.repository;

import com.patika.entity.Connection;
import jakarta.validation.constraints.Email;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConnectionRespository extends JpaRepository<Connection,Long> {
    Connection findByEmail(@Email String email);

    boolean existsByEmail(String email);

}
