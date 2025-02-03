package com.tjtechy.Caching_in_spring_boot.mapper;

import com.tjtechy.Caching_in_spring_boot.entity.Department;
import com.tjtechy.Caching_in_spring_boot.entity.dto.DepartmentDto;

import java.util.List;

public class DepartmentMapper {

  //map entity to Dto ->convert Department to DepartmentDto
  public static DepartmentDto mapFromDepartmentToDepartmentDto(Department department) {
    return new DepartmentDto(
        department.getId(),
        department.getName(),
        department.getLocation(),
            department.getCode()
    );
  }

  //map Dto to entity->convert DepartmentDto to Department
  public static Department mapFromDepartmentDtoToDepartment(DepartmentDto departmentDto) {
   var department = new Department();
    department.setId(departmentDto.departmentId());
    department.setName(departmentDto.name());
    department.setLocation(departmentDto.location());
    department.setCode(departmentDto.code());
    return department;
  }

  //map from List of Department to List of DepartmentDto
  public static List<DepartmentDto> mapFromDepartmentListToDepartmentDtoList(List<Department> departmentList) {
    return departmentList
            .stream()
            .map(DepartmentMapper::mapFromDepartmentToDepartmentDto)
            .toList();
  }

}
