package com.patika.service;

import com.patika.dto.request.DepartmentDto;
import com.patika.entity.Department;
import com.patika.repository.DepartmentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

@Service
public class DepartmentService {
private final DepartmentRepository departmentRepository;

    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }


    public void createDepartment(DepartmentDto departmentDto){

        //aynısı var mı=
          if (departmentRepository.existsByDepartmentCode(departmentDto.getDepartmentCode())){
              throw  new IllegalArgumentException("Department with "+departmentDto.getDepartmentCode()+" already exists");
          }

          Department department = new Department();
          department.setDepartmentName(departmentDto.getDepartmentName());
          department.setDepartmentCode(departmentDto.getDepartmentCode());

          departmentRepository.save(department);

    }

    public DepartmentDto getDepartmentById(Long id) {

          Department department = departmentRepository.findById(id).orElseThrow(
                  ()-> new NoSuchElementException("Department not found with id "+ id )
          );
          DepartmentDto dto = new DepartmentDto();
          dto.setId(department.getId());
          dto.setDepartmentCode(department.getDepartmentCode());
          dto.setDepartmentName(department.getDepartmentName());

        return dto;
    }

    public void updateDepartment(DepartmentDto departmentDto) {
        Department department = departmentRepository.findById(departmentDto.getId()).orElseThrow(
                ()-> new NoSuchElementException("Department not found with id "+departmentDto.getId() )
        );

        department.setDepartmentCode(departmentDto.getDepartmentCode());
        department.setDepartmentName(departmentDto.getDepartmentName());
        department.setUpdatedAt(LocalDateTime.now());
        departmentRepository.save(department);

    }

    public void deleteDepartmentById(Long id) {
        if (!departmentRepository.existsById(id)){
            throw  new IllegalArgumentException("Department with "+id+" not Found");
        }


        departmentRepository.deleteById(id);
    }
}
