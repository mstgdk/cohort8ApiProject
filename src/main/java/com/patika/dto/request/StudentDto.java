package com.patika.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.patika.entity.Connection;
import com.patika.entity.Department;
import com.patika.enums.Gender;
import com.patika.enums.StudentAndInstructorStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentDto {

    private Long id;

    private String firstName;

    private String lastName;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate birthDate;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private Long departmentId;

    private Long connectionId;
}
