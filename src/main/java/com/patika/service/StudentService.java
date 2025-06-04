package com.patika.service;

import com.patika.dto.request.StudentDto;
import com.patika.entity.Connection;
import com.patika.entity.Department;
import com.patika.entity.Student;
import com.patika.exception.ConflictException;
import com.patika.exception.ResourceNotFoundException;
import com.patika.exception.message.ErrorMessage;
import com.patika.mapper.StudentMapper;
import com.patika.repository.ConnectionRespository;
import com.patika.repository.DepartmentRepository;
import com.patika.repository.StudentRepository;
import org.springframework.stereotype.Service;

@Service
public class StudentService {
    private final StudentRepository studentRepository;
    private final ConnectionRespository connectionRespository;
    private final DepartmentRepository departmentRepository;
    //private final StudentMapper studentMapper;

    public StudentService(StudentRepository studentRepository, ConnectionRespository connectionRespository, DepartmentRepository departmentRepository) {
        this.studentRepository = studentRepository;
        this.connectionRespository = connectionRespository;
        this.departmentRepository = departmentRepository;

        //this.studentMapper = studentMapper;
    }

    public void save(StudentDto dto) {

        boolean isExistsStudent = studentRepository.existsByConnectionId(dto.getConnectionId());
        if (isExistsStudent) {
            throw new ConflictException(String.format(ErrorMessage.RESOURCE_ALREADY_EXISTS_EXCEPTION, dto.getConnectionId()));
        }

        Student student = new Student();
        student.setFirstName(dto.getFirstName());
        student.setLastName(dto.getLastName());
        student.setGender(dto.getGender());
        student.setBirthDate(dto.getBirthDate());

        //Student student =studentMapper.toStudent(dto);

        //connection

        Connection connection = connectionRespository.findById(dto.getConnectionId()).orElseThrow(
                () -> new ResourceNotFoundException(ErrorMessage.RESOURCE_NOT_FOUND_EXCEPTION)
        );
        student.setConnection(connection);

        //department
        Department department = departmentRepository.findById(dto.getDepartmentId()).orElseThrow(
                () -> new ResourceNotFoundException(ErrorMessage.RESOURCE_NOT_FOUND_EXCEPTION)
        );
        student.setDepartment(department);

        studentRepository.save(student);
    }

    public void updateStudent(Long id, StudentDto dto) {

        Student student = studentRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(ErrorMessage.RESOURCE_NOT_FOUND_EXCEPTION)
        );

        if (dto.getFirstName() != null) {
            student.setFirstName(dto.getFirstName());
        }
        if (dto.getLastName() != null) {
            student.setLastName(dto.getLastName());
        }
        if (dto.getGender() != null) {
            student.setGender(dto.getGender());
        }
        if (dto.getBirthDate() != null) {
            student.setBirthDate(dto.getBirthDate());
        }


        //connection
        Connection connection = connectionRespository.findById(dto.getConnectionId()).orElseThrow(
                () -> new ResourceNotFoundException(ErrorMessage.RESOURCE_NOT_FOUND_EXCEPTION)
        );
        student.setConnection(connection);

        //department
        Department department = departmentRepository.findById(dto.getDepartmentId()).orElseThrow(
                () -> new ResourceNotFoundException(ErrorMessage.RESOURCE_NOT_FOUND_EXCEPTION)
        );
        student.setDepartment(department);

        studentRepository.save(student);

    }

    public void deleteStudent(Long id) {
        boolean isExists = studentRepository.existsById(id);
        if (!isExists){
            throw  new ResourceNotFoundException(ErrorMessage.RESOURCE_NOT_FOUND_EXCEPTION);
        }
        studentRepository.deleteById(id);
    }
}
