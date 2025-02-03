package com.tjtechy.Caching_in_spring_boot.service;

import com.tjtechy.Caching_in_spring_boot.entity.Department;
import org.springframework.stereotype.Service;

import java.util.List;


public interface DepartmentService {
  Department saveDepartment(Department department);
  Department fetchDepartmentById(Long departmentId);
  List<Department> fetchAllDepartments();
  Department updateDepartment(Long departmentId, Department updateDepartment);
  void deleteDepartment(Long departmentId);

}
