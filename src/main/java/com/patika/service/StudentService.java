package com.patika.service;

import com.filter.student.StudentFilter;
import com.filter.student.StudentSpecification;
import com.patika.dto.request.StudentDto;
import com.patika.dto.request.StudentWithConnectionDto;
import com.patika.entity.Connection;
import com.patika.entity.Department;
import com.patika.entity.ImageFile;
import com.patika.entity.Student;
import com.patika.enums.StudentAndInstructorStatus;
import com.patika.exception.ConflictException;
import com.patika.exception.ResourceNotFoundException;
import com.patika.exception.message.ErrorMessage;
import com.patika.mapper.StudentMapper;
import com.patika.repository.ConnectionRespository;
import com.patika.repository.DepartmentRepository;
import com.patika.repository.StudentRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class StudentService {
    private final StudentRepository studentRepository;
    private final ConnectionRespository connectionRespository;
    private final DepartmentRepository departmentRepository;
    private final StudentMapper studentMapper;
    private final ImageFileService imageFileService;

    public StudentService(StudentRepository studentRepository, ConnectionRespository connectionRespository, DepartmentRepository departmentRepository, StudentMapper studentMapper, ImageFileService imageFileService) {
        this.studentRepository = studentRepository;
        this.connectionRespository = connectionRespository;
        this.departmentRepository = departmentRepository;
        this.studentMapper = studentMapper;
        this.imageFileService = imageFileService;
    }

    public void save(StudentDto dto) {

        boolean isExistsStudent = studentRepository.existsByConnectionId(dto.getConnectionId());
        if (isExistsStudent) {
            throw new ConflictException(String.format(ErrorMessage.RESOURCE_ALREADY_EXISTS_EXCEPTION, dto.getConnectionId()));
        }

     /*   Student student = new Student();
        student.setFirstName(dto.getFirstName());
        student.setLastName(dto.getLastName());
        student.setGender(dto.getGender());
        student.setBirthDate(dto.getBirthDate());*/

        Student student =studentMapper.toStudent(dto);

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

    @Transactional
    public void saveStudentWithConnection(StudentWithConnectionDto dto, String imageId) {
        if (connectionRespository.existsByEmail(dto.getEmail())){
            throw  new ConflictException(String.format(ErrorMessage.RESOURCE_ALREADY_EXISTS_EXCEPTION,dto.getEmail()));
        }
        //yeni connection
        Connection connection = new Connection();
        connection.setEmail(dto.getEmail());
        connection.setMobileNumber(dto.getMobileNumber());
        connectionRespository.save(connection);

        //Student oluştur
        Student student = new Student();
        student.setFirstName(dto.getFirstName());
        student.setLastName(dto.getLastName());
        student.setGender(dto.getGender());
        student.setBirthDate(dto.getBirthDate());
        student.setStatus(StudentAndInstructorStatus.ACTIVE);
        student.setConnection(connection);

        Department department = departmentRepository.findById(dto.getDepartmentId()).orElseThrow(
                () -> new ResourceNotFoundException(ErrorMessage.RESOURCE_NOT_FOUND_EXCEPTION)
        );
        student.setDepartment(department);
        //----image file--------
        ImageFile imageFile = imageFileService.getImageById(imageId);

       Integer count = studentRepository.findStudentCountByImageId(imageFile.getId());
        if (count>0){
            throw  new ConflictException(ErrorMessage.IMAGE_USED_EXCEPTION);
        }
         Set<ImageFile> imfiles = new HashSet<>();
        imfiles.add(imageFile);
        student.setIamges(imfiles);


        studentRepository.save(student);

    }

    public Page<StudentDto> getAllStudentsByDepartmentId(Long departmentId, Pageable pageable) {
        if (!departmentRepository.existsById(departmentId)) {
            throw new ResourceNotFoundException(String.format(ErrorMessage.RESOURCE_NOT_FOUND_EXCEPTION));
        }
        Page<Student> students = studentRepository.findAllByDepartmentId(departmentId, pageable);
        return students.map(studentMapper::toStudentDto);

    }

    public Page<StudentDto> getStudentsByFilter(StudentFilter filter, Pageable pageable) {
        Page<Student> students = studentRepository.findAll(StudentSpecification.build(filter), pageable);
        return students.map(studentMapper::toStudentDto);
    }
}
