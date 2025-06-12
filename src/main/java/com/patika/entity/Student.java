package com.patika.entity;

import com.patika.enums.Gender;
import com.patika.enums.StudentAndInstructorStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Student extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 80)
    private String firstName;

    private String lastName;

    private LocalDate birthDate;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Enumerated(EnumType.STRING)
    private StudentAndInstructorStatus status = StudentAndInstructorStatus.ACTIVE;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "conneciton_id", unique = true)
    private Connection connection;

    @OneToMany(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "student_id")
    private Set<ImageFile> iamges;

}
