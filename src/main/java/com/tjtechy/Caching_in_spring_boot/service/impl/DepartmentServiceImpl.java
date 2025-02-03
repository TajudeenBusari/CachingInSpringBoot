package com.tjtechy.Caching_in_spring_boot.service.impl;

import com.tjtechy.Caching_in_spring_boot.entity.Department;
import com.tjtechy.Caching_in_spring_boot.exception.modelNotFound.DepartmentNotFoundException;
import com.tjtechy.Caching_in_spring_boot.repository.DepartmentRepository;
import com.tjtechy.Caching_in_spring_boot.service.DepartmentService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentServiceImpl implements DepartmentService {
  private final DepartmentRepository departmentRepository;

  public DepartmentServiceImpl(DepartmentRepository departmentRepository) {
    this.departmentRepository = departmentRepository;
  }

  @Override
    public Department saveDepartment(Department department) {

    var savedDepartment = departmentRepository.save(department);
        return savedDepartment;
    }

  @Override
    public Department fetchDepartmentById(Long departmentId) {

        var foundDepartment = departmentRepository.
                findById(departmentId)
                .orElseThrow(() -> new DepartmentNotFoundException(departmentId));
        return foundDepartment;
    }

    @Override
    public List<Department> fetchAllDepartments() {
      List<Department> departments = departmentRepository.findAll();
      return departments;
    }

  @Override
  public Department updateDepartment(Long departmentId, Department updateDepartment) {
    Department foundDepartment = departmentRepository.findById(departmentId)
            .orElseThrow(() -> new DepartmentNotFoundException(departmentId));
    //update the foundDepartment
    foundDepartment.setName(updateDepartment.getName());
    foundDepartment.setLocation(updateDepartment.getLocation());
    foundDepartment.setCode(updateDepartment.getCode());
    //save the updated department
    Department updatedDepartment = departmentRepository.save(foundDepartment);
    return updatedDepartment;
  }

  @Override
  public void deleteDepartment(Long departmentId) {
    departmentRepository.findById(departmentId)
            .orElseThrow(() -> new DepartmentNotFoundException(departmentId));
    departmentRepository.deleteById(departmentId);
  }
}
