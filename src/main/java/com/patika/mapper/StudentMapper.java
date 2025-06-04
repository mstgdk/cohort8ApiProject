package com.patika.mapper;

import com.patika.dto.request.StudentDto;
import com.patika.entity.Student;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
public interface StudentMapper {


    @Mapping(source = "connectionId", target = "connection.id")
    @Mapping(source = "departmentId", target = "department.id")
    Student toStudent(StudentDto studentDto);
}
