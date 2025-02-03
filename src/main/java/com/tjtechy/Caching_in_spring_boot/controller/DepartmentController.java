package com.tjtechy.Caching_in_spring_boot.controller;

import com.tjtechy.Caching_in_spring_boot.entity.dto.DepartmentDto;
import com.tjtechy.Caching_in_spring_boot.service.DepartmentService;
import com.tjtechy.Caching_in_spring_boot.system.Result;
import com.tjtechy.Caching_in_spring_boot.system.StatusCode;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.tjtechy.Caching_in_spring_boot.mapper.DepartmentMapper.*;

@RestController
@RequestMapping("${api.endpoint.base-url}/department")
public class DepartmentController {

  private final DepartmentService departmentService; //inject the interface
  public DepartmentController(DepartmentService departmentService) {
    this.departmentService = departmentService;
  }

  @PostMapping
  public Result createDepartment(@RequestBody DepartmentDto departmentDto) {

    var newDepartment = mapFromDepartmentDtoToDepartment(departmentDto);
    var savedDepartment = departmentService.saveDepartment(newDepartment);
    var savedDepartmentDto = mapFromDepartmentToDepartmentDto(savedDepartment);
    return new Result("Add Success", true, savedDepartmentDto, StatusCode.SUCCESS);
  }

  @GetMapping
  public Result getAllDepartments() {
    var departments = departmentService.fetchAllDepartments();
    List<DepartmentDto> departmentDtos = mapFromDepartmentListToDepartmentDtoList(departments);
    return new Result("Get All Success", true, departmentDtos, StatusCode.SUCCESS);
  }

  @GetMapping("/{id}")
  public Result getDepartmentById(@PathVariable Long id) {
    var department = departmentService.fetchDepartmentById(id);
    var departmentDto = mapFromDepartmentToDepartmentDto(department);
    return new Result("Get One Success", true, departmentDto, StatusCode.SUCCESS);
  }

  @PutMapping("/{id}")
  public Result updateDepartment(@PathVariable Long id, @RequestBody DepartmentDto departmentDto) {
    var department = mapFromDepartmentDtoToDepartment(departmentDto);
    var updatedDepartment = departmentService.updateDepartment(id, department);
    var updatedDepartmentDto = mapFromDepartmentToDepartmentDto(updatedDepartment);
    return new Result("Update Success", true, updatedDepartmentDto, StatusCode.SUCCESS);
  }

  @DeleteMapping("/{id}")
  public Result deleteDepartment(@PathVariable Long id) {
    departmentService.deleteDepartment(id);
    return new Result("Delete Success", true, null, StatusCode.SUCCESS);
  }



}
