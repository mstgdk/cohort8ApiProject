package com.patika.controller;

import com.filter.student.StudentFilter;
import com.patika.dto.request.StudentDto;
import com.patika.dto.request.StudentWithConnectionDto;
import com.patika.dto.response.PatikaResponse;
import com.patika.service.StudentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/student")
public class StudentController {

    private final StudentService studentService;


    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }
    @PostMapping
    public ResponseEntity<PatikaResponse> saveStudent(@RequestBody StudentDto dto){
        studentService.save(dto);

        PatikaResponse response = new PatikaResponse();
        response.setMessage("Student saved");
        response.setSuccess(true);

        return ResponseEntity.ok(response);
    }
    @PutMapping("/{id}")
    public ResponseEntity<PatikaResponse> updateStudent(@PathVariable Long id, @RequestBody StudentDto dto){
        studentService.updateStudent(id,dto);

        PatikaResponse response = new PatikaResponse();
        response.setMessage("Student updated");
        response.setSuccess(true);

        return ResponseEntity.ok(response);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<PatikaResponse> deleteStudent(@PathVariable Long id){
        studentService.deleteStudent(id);

        PatikaResponse response = new PatikaResponse();
        response.setMessage("Student deleted");
        response.setSuccess(true);

        return ResponseEntity.ok(response);
    }
    @PostMapping("/with-connection/{imageId}")
    public ResponseEntity<PatikaResponse> saveStudentWithConnection(@PathVariable String imageId, @RequestBody StudentWithConnectionDto dto){
        studentService.saveStudentWithConnection(dto,imageId);

        PatikaResponse response = new PatikaResponse();
        response.setMessage("Student saved");
        response.setSuccess(true);

        return ResponseEntity.ok(response);
    }
    //PAGEABLE
    @GetMapping("/{departmentId}")
    public ResponseEntity<Page<StudentDto>> getAllStudentsByDepartmentId(@PathVariable Long departmentId,
                                                                         @RequestParam("page") int page,
                                                                         @RequestParam("size") int size,
                                                                         @RequestParam("sort") String prop,
                                                                         @RequestParam(value = "direction",
                                                                                 required = false,
                                                                                 defaultValue = "DESC") Sort.Direction direction) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, prop));
        Page<StudentDto> students = studentService.getAllStudentsByDepartmentId(departmentId, pageable);
        return ResponseEntity.ok(students);
    }
    //FILTER
    @PostMapping("/filter")
    public ResponseEntity<Page<StudentDto>> filterStudents(@RequestBody StudentFilter filter,
                                                           @PageableDefault(size = 10, sort = "createdAt") Pageable pageable) {
        Page<StudentDto>students =  studentService.getStudentsByFilter(filter, pageable);
        return ResponseEntity.ok(students);
    }

}
