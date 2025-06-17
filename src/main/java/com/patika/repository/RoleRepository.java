package com.patika.repository;

import com.patika.entity.Role;
import com.patika.enums.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role,Long> {
    Optional<Role> findByType(RoleType roleType);
}
