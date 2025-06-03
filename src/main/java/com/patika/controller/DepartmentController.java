package com.patika.controller;

import com.patika.dto.request.DepartmentDto;
import com.patika.dto.response.PatikaResponse;
import com.patika.entity.Department;
import com.patika.service.DepartmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/department")
public class DepartmentController {

    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @PostMapping//("/create")
    public ResponseEntity<PatikaResponse> saveDepartment(@RequestBody DepartmentDto departmentDto){
        departmentService.createDepartment(departmentDto);

        PatikaResponse response = new PatikaResponse();
        response.setMessage("Department saved succesfully");
        response.setSuccess(true);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DepartmentDto> getDepartment(@PathVariable Long id){
         DepartmentDto dto =   departmentService.getDepartmentById(id);

        return ResponseEntity.ok(dto);
    }
    @PutMapping
    public ResponseEntity<PatikaResponse> updateDepartment(@RequestBody DepartmentDto departmentDto){
        departmentService.updateDepartment(departmentDto);

        PatikaResponse response = new PatikaResponse();
        response.setMessage("Department updated succesfully");
        response.setSuccess(true);

        return ResponseEntity.ok(response);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<PatikaResponse> removeDepartment(@PathVariable Long id){
        departmentService.deleteDepartmentById(id);
        PatikaResponse response = new PatikaResponse();
        response.setMessage("Department deleted succesfully");
        response.setSuccess(true);
        return ResponseEntity.ok(response);
    }
    @GetMapping
    public  ResponseEntity<List<DepartmentDto>> getAll(){
       List<DepartmentDto> departmentDtos =departmentService.getAll();

       return  new ResponseEntity<>(departmentDtos, HttpStatus.OK);
    }

}
