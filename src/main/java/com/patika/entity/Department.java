package com.patika.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
//@Getter
//@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor//default constructor
public class Department extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "department_name",nullable = false)
    private String departmentName;

    @NotNull
    @Column(unique = true)
    private String departmentCode;
}
