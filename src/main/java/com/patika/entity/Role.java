package com.patika.entity;

import com.patika.enums.RoleType;
import jakarta.persistence.*;

public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    private RoleType type;

    @Override
    public String toString() {
        return "Role{" +
                "type=" + type +
                '}';
    }
}
