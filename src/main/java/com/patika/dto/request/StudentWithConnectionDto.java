package com.patika.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.patika.enums.Gender;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentWithConnectionDto {

    private Long id;

    private String firstName;

    private String lastName;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate birthDate;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private Long departmentId;

   private String email;
   private String mobileNumber;
}
